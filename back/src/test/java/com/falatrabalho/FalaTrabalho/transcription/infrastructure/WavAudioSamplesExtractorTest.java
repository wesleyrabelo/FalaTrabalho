package com.falatrabalho.FalaTrabalho.transcription.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

import com.falatrabalho.FalaTrabalho.transcription.application.TranscriptionException;
import com.falatrabalho.FalaTrabalho.transcription.domain.AudioFile;
import org.junit.jupiter.api.Test;

class WavAudioSamplesExtractorTest {

	private static final int SAMPLE_RATE = 16000;

	private final WavAudioSamplesExtractor samplesExtractor = new WavAudioSamplesExtractor();

	@Test
	void shouldExtractSamplesFromValidWavAudio() {
		byte[] wavContent = loadTestResource("audio/teste-curto.wav");
		AudioFile audioFile = new AudioFile(wavContent, "teste-curto.wav", "audio/wav");

		float[] samples = samplesExtractor.extractSamples(audioFile);

		assertThat(samples).isNotEmpty();
		for (float sample : samples) {
			assertThat(sample).isBetween(-1.0f, 1.0f);
		}
	}

	@Test
	void shouldRejectInvalidAudioContent() {
		AudioFile audioFile = new AudioFile(new byte[] { 1, 2, 3 }, "resposta.wav", "audio/wav");

		assertThatThrownBy(() -> samplesExtractor.extractSamples(audioFile))
				.isInstanceOf(TranscriptionException.class)
				.hasMessage("Unsupported audio format");
	}

	@Test
	void shouldConvertStereoAudioToMonoByAveragingChannels() {
		byte[] wavContent = createPcm16Wav(2, new short[] {
				32767, -32768,
				16384, 16384
		});
		AudioFile audioFile = new AudioFile(wavContent, "estereo.wav", "audio/wav");

		float[] samples = samplesExtractor.extractSamples(audioFile);

		assertThat(samples).hasSize(2);
		assertThat(samples[0]).isCloseTo(-0.000015f, within(0.000001f));
		assertThat(samples[1]).isCloseTo(0.5f, within(0.000001f));
	}

	@Test
	void shouldNormalizePcm16SamplesInsideExpectedRange() {
		byte[] wavContent = createPcm16Wav(1, new short[] {
				Short.MIN_VALUE,
				0,
				Short.MAX_VALUE
		});
		AudioFile audioFile = new AudioFile(wavContent, "extremos.wav", "audio/wav");

		float[] samples = samplesExtractor.extractSamples(audioFile);

		assertThat(samples).hasSize(3);
		assertThat(samples[0]).isEqualTo(-1.0f);
		assertThat(samples[1]).isZero();
		assertThat(samples[2]).isCloseTo(0.999969f, within(0.000001f));
	}

	private byte[] loadTestResource(String resourcePath) {
		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
			assertThat(inputStream).isNotNull();
			return inputStream.readAllBytes();
		}
		catch (IOException exception) {
			throw new UncheckedIOException(exception);
		}
	}

	private byte[] createPcm16Wav(int channels, short[] samples) {
		int byteRate = SAMPLE_RATE * channels * 2;
		int blockAlign = channels * 2;
		int dataSize = samples.length * 2;
		ByteArrayOutputStream output = new ByteArrayOutputStream(44 + dataSize);

		writeAscii(output, "RIFF");
		writeLittleEndianInt(output, 36 + dataSize);
		writeAscii(output, "WAVE");
		writeAscii(output, "fmt ");
		writeLittleEndianInt(output, 16);
		writeLittleEndianShort(output, 1);
		writeLittleEndianShort(output, channels);
		writeLittleEndianInt(output, SAMPLE_RATE);
		writeLittleEndianInt(output, byteRate);
		writeLittleEndianShort(output, blockAlign);
		writeLittleEndianShort(output, 16);
		writeAscii(output, "data");
		writeLittleEndianInt(output, dataSize);

		for (short sample : samples) {
			writeLittleEndianShort(output, sample);
		}

		return output.toByteArray();
	}

	private void writeAscii(ByteArrayOutputStream output, String value) {
		for (char character : value.toCharArray()) {
			output.write(character);
		}
	}

	private void writeLittleEndianInt(ByteArrayOutputStream output, int value) {
		output.write(value & 0xff);
		output.write((value >> 8) & 0xff);
		output.write((value >> 16) & 0xff);
		output.write((value >> 24) & 0xff);
	}

	private void writeLittleEndianShort(ByteArrayOutputStream output, int value) {
		output.write(value & 0xff);
		output.write((value >> 8) & 0xff);
	}
}

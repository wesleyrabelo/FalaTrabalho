package com.falatrabalho.FalaTrabalho.transcription.infrastructure;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.falatrabalho.FalaTrabalho.transcription.application.TranscriptionException;
import com.falatrabalho.FalaTrabalho.transcription.domain.AudioFile;
import org.springframework.stereotype.Component;

@Component
public class WavAudioSamplesExtractor {

	private static final float WHISPER_SAMPLE_RATE = 16000.0f;

	public float[] extractSamples(AudioFile audioFile) {
		try (AudioInputStream sourceStream = AudioSystem.getAudioInputStream(new ByteArrayInputStream(audioFile.content()))) {
			AudioFormat sourceFormat = sourceStream.getFormat();
			int channels = sourceFormat.getChannels();

			if (channels <= 0) {
				throw new TranscriptionException("Audio must have at least one channel");
			}

			AudioFormat pcmFormat = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED,
					WHISPER_SAMPLE_RATE,
					16,
					channels,
					channels * 2,
					WHISPER_SAMPLE_RATE,
					false
			);

			if (!AudioSystem.isConversionSupported(pcmFormat, sourceFormat)) {
				throw new TranscriptionException("Audio cannot be converted to PCM 16 kHz 16-bit: " + sourceFormat);
			}

			try (AudioInputStream pcmStream = AudioSystem.getAudioInputStream(pcmFormat, sourceStream)) {
				byte[] audioBytes = pcmStream.readAllBytes();
				int frameSize = pcmFormat.getFrameSize();
				int totalFrames = audioBytes.length / frameSize;
				float[] samples = new float[totalFrames];

				for (int frame = 0; frame < totalFrames; frame++) {
					int frameOffset = frame * frameSize;
					float sum = 0.0f;

					for (int channel = 0; channel < channels; channel++) {
						int sampleOffset = frameOffset + (channel * 2);
						int low = audioBytes[sampleOffset] & 0xff;
						int high = audioBytes[sampleOffset + 1];
						short sample = (short) ((high << 8) | low);
						sum += sample / 32768.0f;
					}

					samples[frame] = sum / channels;
				}

				return samples;
			}
		}
		catch (UnsupportedAudioFileException exception) {
			throw new TranscriptionException("Unsupported audio format", exception);
		}
		catch (IOException exception) {
			throw new TranscriptionException("Failed to read audio content", exception);
		}
	}
}

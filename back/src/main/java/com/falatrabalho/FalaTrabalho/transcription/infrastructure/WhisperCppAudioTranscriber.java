package com.falatrabalho.FalaTrabalho.transcription.infrastructure;

import java.nio.file.Files;
import java.nio.file.Path;

import com.falatrabalho.FalaTrabalho.transcription.application.AudioTranscriber;
import com.falatrabalho.FalaTrabalho.transcription.application.TranscriptionException;
import com.falatrabalho.FalaTrabalho.transcription.domain.AudioFile;
import com.falatrabalho.FalaTrabalho.transcription.domain.TranscriptionResult;
import io.github.ggerganov.whispercpp.WhisperCpp;
import io.github.ggerganov.whispercpp.params.WhisperFullParams;
import io.github.ggerganov.whispercpp.params.WhisperSamplingStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WhisperCppAudioTranscriber implements AudioTranscriber {

	private final WavAudioSamplesExtractor samplesExtractor;
	private final String modelPath;
	private final String language;
	private final float temperature;
	private final float temperatureIncrement;

	public WhisperCppAudioTranscriber(
			WavAudioSamplesExtractor samplesExtractor,
			@Value("${whisper.cpp.model-path}") String modelPath,
			@Value("${whisper.cpp.language}") String language,
			@Value("${whisper.cpp.temperature}") float temperature,
			@Value("${whisper.cpp.temperature-inc}") float temperatureIncrement
	) {
		this.samplesExtractor = samplesExtractor;
		this.modelPath = modelPath;
		this.language = language;
		this.temperature = temperature;
		this.temperatureIncrement = temperatureIncrement;
	}

	@Override
	public TranscriptionResult transcribe(AudioFile audioFile) {
		if (modelPath == null || modelPath.isBlank()) {
			throw new TranscriptionException("whisper.cpp.model-path must be configured");
		}

		WhisperCpp whisper = new WhisperCpp();
		try {
			Path resolvedModelPath = Path.of(modelPath).toAbsolutePath().normalize();
			if (!Files.isRegularFile(resolvedModelPath)) {
				throw new TranscriptionException("Whisper model file not found: " + resolvedModelPath);
			}

			float[] samples = samplesExtractor.extractSamples(audioFile);
			whisper.initContext(resolvedModelPath.toString());

			WhisperFullParams params = whisper.getFullDefaultParams(WhisperSamplingStrategy.WHISPER_SAMPLING_GREEDY);
			params.language = language;
			params.temperature = temperature;
			params.temperature_inc = temperatureIncrement;

			String transcription = whisper.fullTranscribe(params, samples);
			if (transcription == null || transcription.isBlank()) {
				throw new TranscriptionException("whisper.cpp returned empty transcription");
			}

			return new TranscriptionResult(transcription);
		}
		catch (TranscriptionException exception) {
			throw exception;
		}
		catch (Exception exception) {
			throw new TranscriptionException("Failed to transcribe audio with whisper.cpp", exception);
		}
		finally {
			whisper.close();
		}
	}
}

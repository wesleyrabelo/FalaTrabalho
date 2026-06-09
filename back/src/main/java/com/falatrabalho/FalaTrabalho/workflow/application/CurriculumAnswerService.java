package com.falatrabalho.FalaTrabalho.workflow.application;

import java.io.IOException;

import com.falatrabalho.FalaTrabalho.textimprovement.application.TextEnhancer;
import com.falatrabalho.FalaTrabalho.transcription.application.AudioTranscriber;
import com.falatrabalho.FalaTrabalho.transcription.application.TranscriptionException;
import com.falatrabalho.FalaTrabalho.transcription.domain.AudioFile;
import com.falatrabalho.FalaTrabalho.transcription.domain.TranscriptionResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CurriculumAnswerService {

	private final AudioTranscriber audioTranscriber;
	private final TextEnhancer textEnhancer;

	public CurriculumAnswerService(AudioTranscriber audioTranscriber, TextEnhancer textEnhancer) {
		this.audioTranscriber = audioTranscriber;
		this.textEnhancer = textEnhancer;
	}

	public CurriculumAnswerResponse processAnswer(String field, MultipartFile file) {
		if (field == null || field.isBlank()) {
			throw new IllegalArgumentException("Field must not be blank");
		}
		if (file == null || file.isEmpty()) {
			throw new IllegalArgumentException("Audio file must not be empty");
		}

		AudioFile audioFile = toAudioFile(file);
		TranscriptionResult transcription = audioTranscriber.transcribe(audioFile);
		String improvedText = textEnhancer.enhance(transcription.text());

		return new CurriculumAnswerResponse(
				field,
				transcription.text(),
				improvedText
		);
	}

	private AudioFile toAudioFile(MultipartFile file) {
		try {
			return new AudioFile(
					file.getBytes(),
					file.getOriginalFilename(),
					file.getContentType()
			);
		}
		catch (IOException exception) {
			throw new TranscriptionException("Failed to read uploaded audio file", exception);
		}
	}
}

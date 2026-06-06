package com.falatrabalho.FalaTrabalho.transcription.domain;

public record TranscriptionResult(String text) {

	public TranscriptionResult {
		if (text == null || text.isBlank()) {
			throw new IllegalArgumentException("Transcription text must not be blank");
		}

		text = text.trim();
	}
}

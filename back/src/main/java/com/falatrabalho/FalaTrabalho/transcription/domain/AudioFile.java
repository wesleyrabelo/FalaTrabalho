package com.falatrabalho.FalaTrabalho.transcription.domain;

import java.util.Arrays;

public record AudioFile(byte[] content, String originalFilename, String contentType) {

	private static final String WAV_CONTENT_TYPE = "audio/wav";
	private static final String WAV_X_CONTENT_TYPE = "audio/x-wav";

	public AudioFile {
		if (content == null || content.length == 0) {
			throw new IllegalArgumentException("Audio content must not be empty");
		}
		if (originalFilename == null || originalFilename.isBlank()) {
			throw new IllegalArgumentException("Original filename must not be blank");
		}
		if (!originalFilename.toLowerCase().endsWith(".wav")) {
			throw new IllegalArgumentException("Audio file must use .wav extension");
		}
		if (contentType == null || contentType.isBlank()) {
			throw new IllegalArgumentException("Audio content type must not be blank");
		}
		if (!WAV_CONTENT_TYPE.equalsIgnoreCase(contentType) && !WAV_X_CONTENT_TYPE.equalsIgnoreCase(contentType)) {
			throw new IllegalArgumentException("Audio content type must be WAV");
		}

		content = Arrays.copyOf(content, content.length);
	}

	@Override
	public byte[] content() {
		return Arrays.copyOf(content, content.length);
	}
}

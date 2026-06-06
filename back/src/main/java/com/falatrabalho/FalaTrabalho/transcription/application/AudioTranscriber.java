package com.falatrabalho.FalaTrabalho.transcription.application;

import com.falatrabalho.FalaTrabalho.transcription.domain.AudioFile;
import com.falatrabalho.FalaTrabalho.transcription.domain.TranscriptionResult;

public interface AudioTranscriber {

	TranscriptionResult transcribe(AudioFile audioFile);
}

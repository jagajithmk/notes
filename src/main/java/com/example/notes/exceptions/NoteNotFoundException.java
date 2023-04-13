package com.example.notes.exceptions;

public class NoteNotFoundException extends RuntimeException {

    public NoteNotFoundException() {
        super();
    }

    public NoteNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoteNotFoundException(final String message) {
        super(message);
    }

    public NoteNotFoundException(final Throwable cause) {
        super(cause);
    }
}


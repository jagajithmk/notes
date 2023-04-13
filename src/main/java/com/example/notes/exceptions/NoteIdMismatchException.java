package com.example.notes.exceptions;

public class NoteIdMismatchException extends RuntimeException {

    public NoteIdMismatchException() {
        super();
    }

    public NoteIdMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoteIdMismatchException(final String message) {
        super(message);
    }

    public NoteIdMismatchException(final Throwable cause) {
        super(cause);
    }
}
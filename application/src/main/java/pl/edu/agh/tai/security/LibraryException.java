package pl.edu.agh.tai.security;

public class LibraryException extends Exception {

    public LibraryException(String message, Throwable cause) {
        super(message, cause);
    }

    public LibraryException(String message) {
        super(message);
    }
}

package au.com.live.rajali.pinbowlgame.exception;

public class RollException extends RuntimeException {

    public RollException() { super(); }

    public RollException(final String message) { super(message); }

    public RollException(final Throwable cause) { super(cause); }

    public RollException(final String message, final Throwable cause) {
        super(message, cause);
    }
}

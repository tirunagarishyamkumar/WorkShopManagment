package com.anny.wsm.util;

public class WSMException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -8741390597638777104L;

    public WSMException() {
        super();
    }

    public WSMException(String message, Throwable cause) {
        super(message, cause);
    }

    public WSMException(String message) {
        super(message);
    }

    public WSMException(Throwable cause) {
        super(cause);
    }

}

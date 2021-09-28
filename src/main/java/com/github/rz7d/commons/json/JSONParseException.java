package com.github.rz7d.commons.json;

public class JSONParseException extends JSONException {

    private static final long serialVersionUID = 1L;

    public JSONParseException() {
        super();
    }

    public JSONParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public JSONParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public JSONParseException(String message) {
        super(message);
    }

    public JSONParseException(Throwable cause) {
        super(cause);
    }

}

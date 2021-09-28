package com.github.rz7d.commons.json;

public class JSONSerializeException extends JSONException {

    private static final long serialVersionUID = 1L;

    public JSONSerializeException() {
        super();
    }

    public JSONSerializeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public JSONSerializeException(String message, Throwable cause) {
        super(message, cause);
    }

    public JSONSerializeException(String message) {
        super(message);
    }

    public JSONSerializeException(Throwable cause) {
        super(cause);
    }

}

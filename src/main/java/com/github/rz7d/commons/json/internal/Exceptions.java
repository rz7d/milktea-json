package com.github.rz7d.commons.json.internal;

public final class Exceptions {

    public static void throwUnchecked(Exception exception) {
        throwInternal(exception);
    }

    @SuppressWarnings("unchecked")
    private static <T extends RuntimeException> void throwInternal(Exception exception) {
        throw (T) exception;
    }

}

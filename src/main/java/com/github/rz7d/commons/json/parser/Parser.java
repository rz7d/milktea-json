package com.github.rz7d.commons.json.parser;

import com.github.rz7d.commons.json.JSONParseException;
import com.github.rz7d.commons.json.standard.RFC8259;

import java.nio.CharBuffer;
import java.util.function.Supplier;

public final class Parser {

    private Parser() {
    }

    public static boolean skipWhitespaces(CharBuffer input) {
        while (input.hasRemaining()) {
            char c = input.get();
            if (!RFC8259.isWhitespace(c)) {
                input.position(input.position() - 1);
                return true;
            }
        }
        return false;
    }

    public static <T extends Exception> void validate(char actual, char expected, Supplier<T> exception) throws T {
        if (actual != expected)
            throw exception.get();
    }

    public static void trim(CharBuffer input) {
        if (!skipWhitespaces(input))
            throw new IllegalStateException("Unexpected EOF");
    }

    public static RuntimeException newException(String message) {
        return new JSONParseException(message);
    }

}

package com.github.rz7d.commons.json.parser;

import com.github.rz7d.commons.json.model.JSONValue;

import java.nio.CharBuffer;

public final class LiteralParser {

    private LiteralParser() {
    }

    public static JSONValue parse(CharBuffer buffer, String literal, JSONValue value) {
        int length = literal.length();
        if (buffer.remaining() < length) {
            throw Parser.newException("Length mismatch (buffer < literal)");
        }
        CharBuffer testSequence = buffer.duplicate();
        testSequence.limit(testSequence.position() + length);
        if (!testSequence.equals(CharBuffer.wrap(literal))) {
            String actual = testSequence.toString();
            throw Parser.newException("Unrecognized bool literal: " + actual);
        }
        buffer.position(buffer.position() + length);
        return value;
    }

}

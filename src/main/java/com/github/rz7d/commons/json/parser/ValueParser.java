package com.github.rz7d.commons.json.parser;

import java.nio.CharBuffer;
import java.util.Objects;

import com.github.rz7d.commons.json.standard.RFC8259;
import com.github.rz7d.commons.json.lazy.parser.LazyArrayParser;
import com.github.rz7d.commons.json.lazy.parser.LazyObjectParser;
import com.github.rz7d.commons.json.model.JSONBoolean;
import com.github.rz7d.commons.json.model.JSONNull;
import com.github.rz7d.commons.json.model.JSONValue;

public final class ValueParser {

    public static JSONValue deserialize(CharSequence text) {
        Objects.requireNonNull(text);
        return parse(CharBuffer.wrap(text));
    }

    public static JSONValue parse(CharBuffer input) {
        if (!Parser.skipWhitespaces(input))
            throw Parser.newException("Empty root");

        char c = input.get(input.position());
        switch (c) {
            case RFC8259.BEGIN_OBJECT_CHAR:
                return LazyObjectParser.parse(input);
            case RFC8259.BEGIN_ARRAY_CHAR:
                return LazyArrayParser.parse(input);
            case RFC8259.QUOTATION_MARK:
                return StringParser.parse(input);
            case 't':
                return LiteralParser.parse(input, "true", JSONBoolean.TRUE);
            case 'f':
                return LiteralParser.parse(input, "false", JSONBoolean.FALSE);
            case 'n':
                return LiteralParser.parse(input, "null", JSONNull.NULL);
            case '-':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                return NumberParser.parse(input);
        }
        throw Parser.newException("Invalid token: " + c);
    }

}

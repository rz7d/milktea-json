package com.github.rz7d.commons.json.parser;

import com.github.rz7d.commons.json.immutable.ImmutableJSONString;
import com.github.rz7d.commons.json.model.JSONString;
import com.github.rz7d.commons.json.standard.RFC8259;

import java.nio.CharBuffer;

public final class StringParser {

    private StringParser() {
    }

    public static JSONString parse(CharBuffer input) {
        if (input.get() != RFC8259.QUOTATION_MARK)
            throw Parser.newException("Invalid Character: " + input.get(input.position() - 1));
        return scan(input);
    }

    public static JSONString scan(CharBuffer input) {
        final StringBuilder buffer = new StringBuilder(Math.min(input.length(), 255));
        while (input.hasRemaining()) {
            char c = input.get();
            if (c == RFC8259.QUOTATION_MARK)
                return ImmutableJSONString.of(buffer.toString());
            if (c == '\\')
                c = escape(input);
            buffer.append(c);
        }

        throw Parser.newException("Unexpected end of token");
    }

    public static char escape(CharBuffer buffer) {
        if (!buffer.hasRemaining())
            throw new IllegalStateException("Unexpected EOF");
        char ch = buffer.get();
        switch (ch) {
            case '"':
                return '"';
            case '\\':
                return '\\';
            case '/':
                return '/';
            case 'b':
                return '\b';
            case 'f':
                return '\f';
            case 'n':
                return '\n';
            case 'r':
                return '\r';
            case 't':
                return '\t';
            case 'u':
                int pos = buffer.position();
                final String code = buffer.duplicate().limit(pos + 4).toString();
                buffer.position(pos + 4);
                return (char) Integer.parseUnsignedInt(code, 16);
        }
        return '\\';
    }

}

package com.github.rz7d.commons.json.lazy.parser;

import java.nio.CharBuffer;

import com.github.rz7d.commons.json.model.JSONString;
import com.github.rz7d.commons.json.parser.StringParser;

public final class StringToken extends Token<JSONString> {

    public StringToken(CharBuffer token) {
        super(token);
    }

    @Override
    protected JSONString parse() {
        return StringParser.parse(token);
    }

}

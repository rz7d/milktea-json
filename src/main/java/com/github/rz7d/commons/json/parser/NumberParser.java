package com.github.rz7d.commons.json.parser;

import com.github.rz7d.commons.json.immutable.ImmutableJSONNumber;
import com.github.rz7d.commons.json.standard.RFC8259;
import com.github.rz7d.commons.json.model.JSONNumber;

import java.nio.CharBuffer;

public final class NumberParser {

    public static JSONNumber parse(CharBuffer input) {
        if (!input.hasRemaining())
            throw Parser.newException("Unexcpeted EOF");
        int begin = input.position();
        build:
        while (input.hasRemaining()) {
            char c = input.get();
            switch (c) {
                case RFC8259.END_ARRAY_CHAR:
                case RFC8259.END_OBJECT_CHAR:
                case RFC8259.NAME_SEPARATOR_CHAR:
                case RFC8259.VALUE_SEPARATOR_CHAR:
                    input.position(input.position() - 1);
                    break build;
            }
        }
        int end = input.position();

        final CharBuffer text = input.duplicate();
        text.position(begin).limit(end);
        RFC8259.NumberType type = RFC8259.determineNumberType(text.slice());
        switch (type) {
            case INT:
                return ImmutableJSONNumber.of(Integer.parseInt(text.toString()));
            case INT_EXP:
            case INT_FRAC:
            case INT_FRAC_EXP:
                return ImmutableJSONNumber.of(Double.parseDouble(text.toString()));
            default:
                break;
        }
        throw Parser.newException("token " + text + " is not a valid number");
    }

}

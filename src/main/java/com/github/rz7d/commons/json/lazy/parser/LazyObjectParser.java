package com.github.rz7d.commons.json.lazy.parser;

import com.github.rz7d.commons.json.lazy.LazyJSONObject;
import com.github.rz7d.commons.json.model.JSONObject;
import com.github.rz7d.commons.json.parser.Parser;
import com.github.rz7d.commons.json.parser.StringParser;
import com.github.rz7d.commons.json.standard.RFC8259;

import java.nio.CharBuffer;
import java.util.LinkedHashMap;
import java.util.Map;

public class LazyObjectParser {

    private static final int PHASE_READ_NAME_OR_END_OF_OBJECT = 0;
    private static final int PHASE_VALIDATE_NAME_SEPARATOR = 1;
    private static final int PHASE_NEXT_PROPERTY_OR_END_OF_OBJECT = 2;

    public static JSONObject parse(CharBuffer input) {
        if (!input.hasRemaining()) {
            throw Parser.newException("Empty Object");
        }
        Parser.validate(input.get(), RFC8259.BEGIN_OBJECT_CHAR,
            () -> Parser.newException("Character 0 must be a begin object character"));
        return new LazyJSONObject(scan(input));
    }

    public static Map<String, ValueToken> scan(CharBuffer input) {
        Map<String, ValueToken> builder = new LinkedHashMap<>();
        int phase = PHASE_READ_NAME_OR_END_OF_OBJECT;
        String key = (String) null;

        for (char c; input.hasRemaining(); phase = (phase + 1) % 3) {
            Parser.trim(input);
            c = input.get();
            switch (phase) {
                case PHASE_READ_NAME_OR_END_OF_OBJECT:
                    if (c == RFC8259.END_OBJECT_CHAR) {
                        return builder;
                    }
                    Parser.validate(c, RFC8259.QUOTATION_MARK,
                        () -> Parser.newException("object name is must be String."));
                    key = StringParser.scan(input).value();
                    continue;
                case PHASE_VALIDATE_NAME_SEPARATOR:
                    if (c == RFC8259.NAME_SEPARATOR_CHAR) {
                        assert key != null;
                        Parser.trim(input);
                        builder.put(key, ValueToken.create(input));
                        continue;
                    }
                    throw Parser.newException("Invalid name-separator " + c);
                case PHASE_NEXT_PROPERTY_OR_END_OF_OBJECT:
                    switch (c) {
                        case RFC8259.VALUE_SEPARATOR_CHAR:
                            continue;
                        case RFC8259.END_OBJECT_CHAR:
                            return builder;
                        default:
                            throw Parser.newException("Must be: value-separator ',' or end of object '}' Found: " + c);
                    }
                default:
                    assert false : "Corrupted coroutine";
            }
        }
        throw Parser.newException("Empty Object");
    }

}

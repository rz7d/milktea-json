package com.github.rz7d.commons.json.lazy.parser;

import com.github.rz7d.commons.json.lazy.LazyJSONArray;
import com.github.rz7d.commons.json.model.JSONArray;
import com.github.rz7d.commons.json.parser.Parser;
import com.github.rz7d.commons.json.standard.RFC8259;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

public final class LazyArrayParser {

    private LazyArrayParser() {
    }

    public static JSONArray parse(CharBuffer input) {
        return new LazyJSONArray(scan(input));
    }

    public static List<ValueToken> scan(CharBuffer input) {
        if (input.get() != RFC8259.BEGIN_ARRAY_CHAR)
            throw Parser.newException("Invalid Character: " + input.get(input.position() - 1));

        // FIXME: 空配列のときに空のValueTokenが生成されてInvalid token になる
        // factory method で対処
        List<ValueToken> builder = new ArrayList<>();
        while (input.hasRemaining()) {
            Parser.trim(input);
            ValueToken token = ValueToken.create(input);
            if (token == null) {
                return builder;
            }
            builder.add(token);
            Parser.trim(input);
            char c = input.get();
            switch (c) {
                case RFC8259.VALUE_SEPARATOR_CHAR:
                    continue;
                case RFC8259.END_ARRAY_CHAR:
                    return builder;
            }
            // [0, 1, 3, 4, 5, 6, 7, 8, 9, 0]
            // []
            // [0]
        }
        throw Parser.newException("Unexpected EOF");
    }

}

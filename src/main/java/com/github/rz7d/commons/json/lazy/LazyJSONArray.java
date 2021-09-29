package com.github.rz7d.commons.json.lazy;

import com.github.rz7d.commons.json.model.JSONArray;
import com.github.rz7d.commons.json.model.JSONValue;
import com.github.rz7d.commons.json.parser.Parser;
import com.github.rz7d.commons.json.standard.RFC8259;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Stream;

public final class LazyJSONArray implements JSONArray {

    public static JSONArray parse(CharBuffer input) {
        return new LazyJSONArray(scan(input));
    }

    public static List<LazyToken> scan(CharBuffer input) {
        if (input.get() != RFC8259.BEGIN_ARRAY_CHAR)
            throw Parser.newException("Invalid Character: " + input.get(input.position() - 1));

        // FIXME: 空配列のときに空のValueTokenが生成されてInvalid token になる
        // factory method で対処
        List<LazyToken> builder = new ArrayList<>();
        while (input.hasRemaining()) {
            Parser.trim(input);
            LazyToken token = LazyToken.create(input);
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

    private final List<LazyToken> list;

    public LazyJSONArray(List<LazyToken> list) {
        this.list = list;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public JSONValue get(int index) {
        LazyToken token = list.get(index);
        if (token == null)
            return null;
        return token.get();
    }

    @Override
    public Stream<JSONValue> stream() {
        return list.stream().map(LazyToken::get);
    }

    @Override
    public Iterator<JSONValue> iterator() {
        return stream().iterator();
    }

    @Override
    public Spliterator<JSONValue> spliterator() {
        return stream().spliterator();
    }

}

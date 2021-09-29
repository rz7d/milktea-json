package com.github.rz7d.commons.json.lazy;

import com.github.rz7d.commons.json.internal.Iterables;
import com.github.rz7d.commons.json.model.JSONObject;
import com.github.rz7d.commons.json.model.JSONValue;
import com.github.rz7d.commons.json.parser.Parser;
import com.github.rz7d.commons.json.parser.StringParser;
import com.github.rz7d.commons.json.standard.RFC8259;

import java.nio.CharBuffer;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class LazyJSONObject implements JSONObject {

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

    public static Map<String, LazyToken> scan(CharBuffer input) {
        Map<String, LazyToken> builder = new LinkedHashMap<>();
        int phase = PHASE_READ_NAME_OR_END_OF_OBJECT;
        String key = null;

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
                        builder.put(key, LazyToken.create(input));
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

    private final Map<String, LazyToken> map;

    public LazyJSONObject(Map<String, LazyToken> map) {
        this.map = map;
    }

    @Override
    public JSONValue get(String name) {
        final LazyToken token = map.get(name);
        if (token == null)
            return null;
        return token.get();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public Iterator<Entry<String, JSONValue>> iterator() {
        return map.entrySet()
            .stream()
            .<Entry<String, JSONValue>>map(e -> new SimpleImmutableEntry<>(e.getKey(), e.getValue().get()))
            .iterator();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof JSONObject && Iterables.equals(((JSONObject) obj), this);
    }

}

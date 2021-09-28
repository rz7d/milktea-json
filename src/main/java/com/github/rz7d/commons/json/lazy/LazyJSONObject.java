package com.github.rz7d.commons.json.lazy;

import com.github.rz7d.commons.json.internal.Iterables;
import com.github.rz7d.commons.json.lazy.parser.ValueToken;
import com.github.rz7d.commons.json.model.JSONObject;
import com.github.rz7d.commons.json.model.JSONValue;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public final class LazyJSONObject implements JSONObject {

    private final Map<String, ValueToken> map;

    public LazyJSONObject(Map<String, ValueToken> map) {
        this.map = map;
    }

    @Override
    public JSONValue get(String name) {
        final ValueToken token = map.get(name);
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
            .<Entry<String, JSONValue>>map(e -> new SimpleImmutableEntry<>(
                e.getKey(), e.getValue().get()))
            .iterator();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof JSONObject && Iterables.equals(((JSONObject) obj), this);
    }

}

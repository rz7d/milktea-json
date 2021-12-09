package com.github.rz7d.commons.json.immutable;

import com.github.rz7d.commons.json.internal.Iterables;
import com.github.rz7d.commons.json.model.JSONObject;
import com.github.rz7d.commons.json.model.JSONValue;

import java.util.*;
import java.util.Map.Entry;

public final class ImmutableJSONObject extends ImmutableJSONValue implements JSONObject {

    private static final ImmutableJSONObject EMPTY = new ImmutableJSONObject(Collections.emptyMap());

    public static ImmutableJSONObject empty() {
        return EMPTY;
    }

    public static ImmutableJSONObject of(Map<String, ? extends JSONValue> map) {
        return new ImmutableJSONObject(new LinkedHashMap<>(map));
    }

    public static JSONObject.Builder builder() {
        return new Builder();
    }

    private static final class Builder implements JSONObject.Builder {

        private final HashMap<String, JSONValue> map = new LinkedHashMap<>();

        Builder() {
        }

        @Override
        public void accept(String key, JSONValue value) {
            map.put(key, value);
        }

        @Override
        public JSONObject build() {
            if (map.isEmpty()) {
                return EMPTY;
            }
            return new ImmutableJSONObject(map);
        }

    }

    private final Map<String, JSONValue> map;

    private ImmutableJSONObject(Map<String, JSONValue> map) {
        this.map = map;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public JSONValue get(String name) {
        return map.get(name);
    }

    @Override
    public Iterator<Entry<String, JSONValue>> iterator() {
        return map.entrySet().iterator();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.map == null) ? 0 : this.map.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof JSONObject && Iterables.equals(((JSONObject) obj), this);
    }

}

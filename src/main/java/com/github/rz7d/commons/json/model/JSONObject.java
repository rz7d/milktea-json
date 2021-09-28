package com.github.rz7d.commons.json.model;

import com.github.rz7d.commons.json.immutable.ImmutableJSONObject;

import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

public interface JSONObject extends JSONValue, Iterable<Map.Entry<String, JSONValue>> {

    static JSONObject of(Map<String, ? extends JSONValue> map) {
        return ImmutableJSONObject.of(map);
    }

    static JSONObject empty() {
        return ImmutableJSONObject.empty();
    }

    static Builder builder() {
        return ImmutableJSONObject.builder();
    }

    @Override
    default JSONObject asObject() {
        return this;
    }

    int size();

    JSONValue get(String name);

    default JSONObject getObject(String name) {
        return (JSONObject) get(name);
    }

    default JSONArray getArray(String name) {
        return (JSONArray) get(name);
    }

    default JSONNumber getNumber(String name) {
        return (JSONNumber) get(name);
    }

    default JSONString getString(String name) {
        return (JSONString) get(name);
    }

    default JSONBoolean getBoolean(String name) {
        return (JSONBoolean) get(name);
    }

    default void forEach(BiConsumer<String, JSONValue> action) {
        Objects.requireNonNull(action);
        for (Map.Entry<String, JSONValue> entry : this) {
            action.accept(entry.getKey(), entry.getValue());
        }
    }

    interface Builder extends BiConsumer<String, JSONValue> {

        @Override
        void accept(String key, JSONValue value);

        default void accept(String key, boolean value) {
            accept(key, JSONBoolean.of(value));
        }

        default void accept(String key, Number value) {
            accept(key, JSONNumber.of(value));
        }

        default void accept(String key, String value) {
            accept(key, JSONString.of(value));
        }

        default Builder add(String key, JSONValue value) {
            accept(key, value);
            return this;
        }

        default Builder add(String key, boolean value) {
            accept(key, value);
            return this;
        }

        default Builder add(String key, Number value) {
            accept(key, value);
            return this;
        }

        default Builder add(String key, String value) {
            accept(key, value);
            return this;
        }

        JSONObject build();

    }

}

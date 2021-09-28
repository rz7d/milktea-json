package com.github.rz7d.commons.json.model;

import com.github.rz7d.commons.json.immutable.ImmutableJSONArray;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface JSONArray extends JSONValue, Iterable<JSONValue> {

    interface Builder extends Consumer<JSONValue> {

        @Override
        void accept(JSONValue element);

        default void accept(boolean value) {
            accept(JSONBoolean.of(value));
        }

        default void accept(Number value) {
            accept(JSONNumber.of(value));
        }

        default void accept(String value) {
            accept(JSONString.of(value));
        }

        default Builder add(JSONValue value) {
            accept(value);
            return this;
        }

        default Builder add(boolean value) {
            accept(value);
            return this;
        }

        default Builder add(Number value) {
            accept(value);
            return this;
        }

        default Builder add(String value) {
            accept(value);
            return this;
        }

        JSONArray build();

    }

    static JSONArray of(List<JSONValue> values) {
        return ImmutableJSONArray.of(values);
    }

    static JSONArray empty() {
        return ImmutableJSONArray.empty();
    }

    static Builder builder() {
        return ImmutableJSONArray.builder();
    }

    default Stream<JSONValue> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    default Stream<JSONValue> parallelStream() {
        return StreamSupport.stream(spliterator(), true);
    }

    // Query Operations

    @Override
    default JSONArray asArray() {
        return this;
    }

    int size();

    JSONValue get(int index);

    default JSONObject getObject(int index) {
        return (JSONObject) get(index);
    }

    default JSONArray getArray(int index) {
        return (JSONArray) get(index);
    }

    default JSONNumber getNumber(int index) {
        return (JSONNumber) get(index);
    }

    default JSONString getString(int index) {
        return (JSONString) get(index);
    }

    default JSONBoolean getBoolean(int index) {
        return (JSONBoolean) get(index);
    }

}

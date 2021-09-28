package com.github.rz7d.commons.json.immutable;

import com.github.rz7d.commons.json.internal.Cache;
import com.github.rz7d.commons.json.model.JSONString;

import java.util.Objects;

public final class ImmutableJSONString extends ImmutableJSONValue implements JSONString {

    private static final ImmutableJSONString EMPTY = new ImmutableJSONString("");

    private static final Cache<String, ImmutableJSONString> CACHE = new Cache<>(ImmutableJSONString::new);

    public static ImmutableJSONString empty() {
        return EMPTY;
    }

    public static ImmutableJSONString of(String value) {
        return value.isEmpty() ? EMPTY : CACHE.get(value);
    }

    private final String value;

    private ImmutableJSONString(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof JSONString && Objects.equals(((JSONString) obj).value(), value());
    }

}

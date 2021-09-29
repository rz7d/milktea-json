package com.github.rz7d.commons.json.immutable;

import com.github.rz7d.commons.json.model.JSONString;

import java.util.Objects;

/**
 * This implementation is compliant with <a href="https://docs.oracle.com/javase/jp/8/docs/api/java/lang/doc-files/ValueBased.html">value-based class specs</a>.
 */
public final class ImmutableJSONString extends ImmutableJSONValue implements JSONString {

    private static final ImmutableJSONString EMPTY = new ImmutableJSONString("");

    /**
     * Returns empty instance that represents empty JSON string.
     *
     * @return An empty instance that represents empty JSON string.
     */
    public static ImmutableJSONString empty() {
        return EMPTY;
    }

    /**
     * Create JSON string from specified Java string.
     *
     * @param value Value for JSON string.
     * @return JSON String instance with immutability.
     */
    public static ImmutableJSONString of(String value) {
        return value.isEmpty() ? EMPTY : new ImmutableJSONString(value);
    }

    /**
     * String value in Java representation.
     */
    private final String value;

    private ImmutableJSONString(String value) {
        this.value = value;
    }

    /**
     * Returns value as java.lang.String in Java representation.
     *
     * @return the value in Java representation.
     */
    @Override
    public String value() {
        return value;
    }

    /**
     * Same as {@link this#value()}.hashCode()
     *
     * @return result of {@link this#value()}.hashCode()
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Checks value-based equality.
     * In this context, "value" represents {@link this#value()}.
     *
     * @param obj object to test
     * @return {@link this#value()} and ((JSONString) obj).value() are equal.
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof JSONString && Objects.equals(((JSONString) obj).value(), value());
    }

    @Override
    public String toString() {
        return value();
    }

}

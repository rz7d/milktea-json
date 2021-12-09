package com.github.rz7d.commons.json.immutable;

import com.github.rz7d.commons.json.model.JSONBoolean;

@SuppressWarnings("serial")
public final class ImmutableJSONBoolean extends ImmutableJSONValue implements JSONBoolean {

    public static final ImmutableJSONBoolean TRUE = new ImmutableJSONBoolean();
    public static final ImmutableJSONBoolean FALSE = new ImmutableJSONBoolean();

    private ImmutableJSONBoolean() {
    }

    @Override
    public boolean boolValue() {
        return this == TRUE;
    }

    @Override
    public int hashCode() {
        return boolValue() ? 1231 : 1237;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this;
    }

    @Override
    public String toString() {
        return this == TRUE ? "true" : "false";
    }

}

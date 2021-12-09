package com.github.rz7d.commons.json.immutable;

import com.github.rz7d.commons.json.model.JSONNull;

@SuppressWarnings("serial")
public final class ImmutableJSONNull extends ImmutableJSONValue implements JSONNull {

    public static final ImmutableJSONNull NULL = new ImmutableJSONNull();

    private ImmutableJSONNull() {
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "null";
    }

}

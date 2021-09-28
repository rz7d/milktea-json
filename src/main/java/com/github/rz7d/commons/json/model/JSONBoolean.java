package com.github.rz7d.commons.json.model;

import com.github.rz7d.commons.json.immutable.ImmutableJSONBoolean;
import com.github.rz7d.commons.json.model.JSONPrimitive.OfBoolean;

public interface JSONBoolean extends JSONValue, OfBoolean {

    JSONBoolean TRUE = ImmutableJSONBoolean.TRUE;
    JSONBoolean FALSE = ImmutableJSONBoolean.FALSE;

    static JSONBoolean of(boolean value) {
        return value ? TRUE : FALSE;
    }

    @Override
    default JSONBoolean asBoolean() {
        return this;
    }

}

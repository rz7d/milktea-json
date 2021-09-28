package com.github.rz7d.commons.json.model;

import com.github.rz7d.commons.json.immutable.ImmutableJSONString;

public interface JSONString extends JSONValue, JSONPrimitive<String> {

    static JSONString of(String value) {
        return ImmutableJSONString.of(value);
    }

    @Override
    default JSONString asString() {
        return this;
    }

}

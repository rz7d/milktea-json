package com.github.rz7d.commons.json.model;

import com.github.rz7d.commons.json.immutable.ImmutableJSONNull;

public interface JSONNull extends JSONValue {

    JSONNull NULL = ImmutableJSONNull.NULL;

    @Override
    default JSONNull asNull() {
        return this;
    }

}

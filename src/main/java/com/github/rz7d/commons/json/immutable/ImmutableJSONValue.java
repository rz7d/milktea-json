package com.github.rz7d.commons.json.immutable;

import com.github.rz7d.commons.json.JSON;
import com.github.rz7d.commons.json.model.JSONValue;

abstract class ImmutableJSONValue implements JSONValue {

    @Override
    public String toString() {
        return JSON.serialize(this);
    }

}

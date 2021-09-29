package com.github.rz7d.commons.json.lazy;

import com.github.rz7d.commons.json.model.JSONValue;

public abstract class LazyJSONValue<V extends JSONValue> implements JSONValue {

    protected final Lazy<V> token;

    public LazyJSONValue(Lazy<V> token) {
        this.token = token;
    }

}

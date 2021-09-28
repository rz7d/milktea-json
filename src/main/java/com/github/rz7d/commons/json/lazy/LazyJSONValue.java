package com.github.rz7d.commons.json.lazy;

import com.github.rz7d.commons.json.model.JSONValue;
import com.github.rz7d.commons.json.lazy.parser.Token;

public abstract class LazyJSONValue<V extends JSONValue> implements JSONValue {

    protected final Token<V> token;

    public LazyJSONValue(Token<V> token) {
        this.token = token;
    }

}

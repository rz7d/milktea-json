package com.github.rz7d.commons.json.lazy;

import com.github.rz7d.commons.json.model.JSONString;
import com.github.rz7d.commons.json.lazy.parser.Token;

public final class LazyJSONString extends LazyJSONValue<JSONString> implements JSONString {

    public LazyJSONString(Token<JSONString> token) {
        super(token);
    }

    @Override
    public String value() {
        return token.get().value();
    }

}

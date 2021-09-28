package com.github.rz7d.commons.json.model;

import com.github.rz7d.commons.json.immutable.ImmutableJSONNumber;

import java.math.BigDecimal;

public interface JSONNumber extends JSONValue, JSONPrimitive<Number> {

    static JSONNumber of(Number value) {
        return ImmutableJSONNumber.of(value);
    }

    @Override
    default JSONNumber asNumber() {
        return this;
    }

    default BigDecimal toBigDecimal() {
        return new BigDecimal(value().toString());
    }

}

package com.github.rz7d.commons.json.model;

public interface JSONPrimitive<V> {

    V value();

    interface OfBoolean extends JSONPrimitive<Boolean> {

        boolean boolValue();

        @Override
        default Boolean value() {
            return boolValue();
        }

    }

}

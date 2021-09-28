package com.github.rz7d.commons.json.serializer;

public interface Serializer<V> {

    void serialize(StringBuilder builder, V value);

}

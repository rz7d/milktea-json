package com.github.rz7d.commons.json.serializer;

import com.github.rz7d.commons.json.model.*;

public final class ValueSerializer implements Serializer<JSONValue> {

    private static final ValueSerializer INSTANCE = new ValueSerializer();
    private final ArraySerializer arraySerializer = new ArraySerializer(this);
    private final ObjectSerializer objectSerializer = new ObjectSerializer(this);
    private final StringSerializer stringSerializer = new StringSerializer();
    private ValueSerializer() {
    }

    public static String serialize(JSONValue value) {
        final StringBuilder builder = new StringBuilder();
        INSTANCE.serialize(builder, value);
        return builder.toString();
    }

    @Override
    public void serialize(StringBuilder builder, JSONValue value) {
        if (value instanceof JSONArray) {
            arraySerializer.serialize(builder, (JSONArray) value);
        }
        if (value instanceof JSONObject) {
            objectSerializer.serialize(builder, (JSONObject) value);
        }
        if (value instanceof JSONString) {
            stringSerializer.serialize(builder, (JSONString) value);
        }
        if (value instanceof JSONNumber) {
            builder.append(((JSONNumber) value).value());
        }
        if (value instanceof JSONBoolean) {
            builder.append(((JSONBoolean) value).value());
        }
        if (value instanceof JSONNull) {
            builder.append((String) null);
        }
    }

}

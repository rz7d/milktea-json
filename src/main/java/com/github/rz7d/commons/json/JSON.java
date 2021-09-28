package com.github.rz7d.commons.json;

import com.github.rz7d.commons.json.model.JSONValue;
import com.github.rz7d.commons.json.parser.ValueParser;
import com.github.rz7d.commons.json.serializer.ValueSerializer;

public final class JSON {

    public static JSONValue parse(String json) {
        return ValueParser.deserialize(json);
    }

    public static String serialize(JSONValue value) {
        return ValueSerializer.serialize(value);
    }

}

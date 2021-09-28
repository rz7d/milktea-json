package com.github.rz7d.commons.json.serializer;

import com.github.rz7d.commons.json.model.JSONArray;
import com.github.rz7d.commons.json.model.JSONValue;
import com.github.rz7d.commons.json.standard.RFC8259;

public final class ArraySerializer implements Serializer<JSONArray> {

    private final ValueSerializer serializer;

    public ArraySerializer(ValueSerializer serializer) {
        this.serializer = serializer;
    }

    public void serialize(StringBuilder builder, JSONArray array) {
        builder.append(RFC8259.BEGIN_ARRAY_CHAR);
        boolean first = true;
        for (JSONValue value : array) {
            if (!first)
                builder.append(RFC8259.VALUE_SEPARATOR_CHAR);
            serializer.serialize(builder, value);
            first = false;
        }
        builder.append(RFC8259.END_ARRAY_CHAR);
    }

}

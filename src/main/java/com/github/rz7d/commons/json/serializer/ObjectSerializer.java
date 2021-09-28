package com.github.rz7d.commons.json.serializer;

import com.github.rz7d.commons.json.model.JSONObject;
import com.github.rz7d.commons.json.model.JSONValue;
import com.github.rz7d.commons.json.standard.RFC8259;

import java.util.Map;

public final class ObjectSerializer implements Serializer<JSONObject> {

    private final ValueSerializer serializer;

    public ObjectSerializer(ValueSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public void serialize(StringBuilder builder, JSONObject object) {
        builder.append(RFC8259.BEGIN_OBJECT_CHAR);
        boolean first = true;
        for (Map.Entry<String, JSONValue> entry : object) {
            if (!first)
                builder.append(RFC8259.VALUE_SEPARATOR_CHAR);
            String key = entry.getKey();
            builder.ensureCapacity(3 + key.length());
            builder
                .append(RFC8259.QUOTATION_MARK)
                .append(key)
                .append(RFC8259.QUOTATION_MARK)
                .append(RFC8259.NAME_SEPARATOR_CHAR);
            serializer.serialize(builder, entry.getValue());
            first = false;
        }
        builder.append(RFC8259.END_OBJECT_CHAR);
    }

}

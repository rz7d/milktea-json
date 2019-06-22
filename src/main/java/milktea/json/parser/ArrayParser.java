package milktea.json.parser;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

import milktea.json.JSON;
import milktea.json.element.JSONArray;
import milktea.json.element.JSONValue;
import milktea.json.immutable.ImmutableJSONArray;

class ArrayParser {

    public static JSONArray deserialize(CharBuffer buffer) {
        if (buffer.get() != JSON.BEGIN_ARRAY_CHAR)
            throw Parser.newException("Invalid Character: " + buffer.get(buffer.position() - 1));

        var array = new ArrayList<JSONValue>();
        while (buffer.hasRemaining()) {
            if (!Parser.skipWhitespaces(buffer))
                throw Parser.newException("Unexpected EOF");
            array.add(ValueParser.deserialize(buffer));
            if (!Parser.skipWhitespaces(buffer))
                throw Parser.newException("Unexpected EOF");
            var c = buffer.get();
            switch (c) {
            case JSON.VALUE_SEPARATOR_CHAR:
                continue;
            case JSON.END_ARRAY_CHAR:
                return new ImmutableJSONArray(List.copyOf(array));
            }
        }
        throw Parser.newException("Unexpected EOF");
    }

}

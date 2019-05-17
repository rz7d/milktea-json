package milktea.json.deserializer;

import milktea.json.JSON;
import milktea.json.element.JSONValue;
import milktea.json.immutable.ImmutableJSONObject;

import java.nio.CharBuffer;
import java.util.HashMap;

class ObjectDeserializer {

    private static final int PHASE_READ_NAME = 0;
    private static final int PHASE_VALIDATE_NAME_SEPARATOR = 1;
    private static final int PHASE_READ_MORE_OR_END_OF_OBJECT = 2;

    public static JSONValue deserialize(CharBuffer buffer) {
        if (buffer.get() != JSON.BEGIN_OBJECT_CHAR)
            throw Deserializer.newException("Invalid Character: " + buffer.get(buffer.position() - 1));

        var map = new HashMap<String, JSONValue>();
        var phase = PHASE_READ_NAME;
        var key = (String) null;

        for (char c; buffer.hasRemaining(); phase = (phase + 1) % 3) {
            if (!Deserializer.skipWhitespaces(buffer))
                throw new IllegalStateException("Unexpected EOF");
            c = buffer.get();
            switch (phase) {
                case PHASE_READ_NAME:
                    switch (c) {
                        case JSON.QUOTATION_MARK:
                            buffer.position(buffer.position() - 1);
                            key = StringDeserializer.deserialize(buffer).toString();
                            continue;
                        default:
                            throw Deserializer.newException("object name is must be String.");
                    }
                case PHASE_VALIDATE_NAME_SEPARATOR:
                    switch (c) {
                        case JSON.NAME_SEPARATOR_CHAR:
                            assert key != null;
                            map.put(key, ValueDeserializer.deserialize(buffer));
                            continue;
                        default:
                            throw Deserializer.newException("Invalid name-separator " + c);
                    }
                case PHASE_READ_MORE_OR_END_OF_OBJECT:
                    switch (c) {
                        case JSON.VALUE_SEPARATOR_CHAR:
                            continue;
                        case JSON.END_OBJECT_CHAR:
                            return new ImmutableJSONObject(map);
                        default:
                            throw Deserializer.newException("Must be: value-separator ',' or end of object '}' Found: " + c);
                    }
                default:
                    throw Deserializer.newException("Corrupted Coroutine");
            }
        }
        throw Deserializer.newException("Empty Object");
    }

}

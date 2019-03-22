package milktea.json.deserializer;

import java.nio.CharBuffer;
import java.util.ArrayList;

import milktea.json.JSON;
import milktea.json.element.JSONArray;
import milktea.json.element.JSONValue;
import milktea.json.immutable.ImmutableJSONArray;

class ArrayDeserializer {

  public static JSONArray deserialize(CharBuffer buffer) {
    if (buffer.get() != JSON.BEGIN_ARRAY_CHAR)
      throw Deserializer.newException("Invalid Character: " + buffer.get(buffer.position() - 1));

    var array = new ArrayList<JSONValue>();
    while (buffer.hasRemaining()) {
      if (!Deserializer.skipWhitespaces(buffer))
        throw Deserializer.newException("Unexpected EOF");
      array.add(ValueDeserializer.deserialize(buffer));
      if (!Deserializer.skipWhitespaces(buffer))
        throw Deserializer.newException("Unexpected EOF");
      var c = buffer.get();
      switch (c) {
        case JSON.VALUE_SEPARATOR_CHAR:
          continue;
        case JSON.END_ARRAY_CHAR:
          return new ImmutableJSONArray(array.toArray(new JSONValue[array.size()]));
      }
    }
    throw Deserializer.newException("Unexpected EOF");
  }

}

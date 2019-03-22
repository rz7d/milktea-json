package milktea.json.deserializer;

import java.nio.CharBuffer;

import milktea.json.JSON;
import milktea.json.JSONException;

public interface Deserializer {

  static boolean skipWhitespaces(CharBuffer buffer) {
    while (buffer.hasRemaining()) {
      char c = buffer.get();
      if (!JSON.isWhitespace(c)) {
        buffer.position(buffer.position() - 1);
        return true;
      }
    }
    return false;
  }

  static RuntimeException newException(String message) {
    return new JSONException(message);
  }

}

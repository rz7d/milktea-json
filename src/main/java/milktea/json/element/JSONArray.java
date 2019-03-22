package milktea.json.element;

import java.util.Objects;

public interface JSONArray extends JSONValue {

  @Override
  default boolean isArray() {
    return true;
  }

  JSONValue[] toArray();

  int length();

  default JSONValue get(int index) {
    return toArray()[index];
  }

  default <T> T get(int index, Class<T> type) {
    var v = get(index);
    if (v == null)
      return null;
    return v.convert(type);
  }

  default JSONObject getObject(int index) {
    final JSONValue v = get(index);
    if (v.isObject())
      return (JSONObject) v;
    return null;
  }

  default JSONArray getArray(int index) {
    final JSONValue v = get(index);
    if (v.isArray())
      return (JSONArray) v;
    return null;
  }

  default JSONNumber getNumber(int index) {
    final JSONValue v = get(index);
    if (v.isNumber())
      return (JSONNumber) v;
    return null;
  }

  default String getString(int index) {
    final JSONValue v = get(index);
    if (v.isString())
      return ((JSONString) v).value();
    return null;
  }

  default boolean getBoolean(int index) {
    final JSONValue v = get(index);
    if (v.isBoolean())
      return ((JSONBoolean) v).value();
    throw new ClassCastException();
  }

  @Override
  @SuppressWarnings("unchecked")
  default <T> T convert(Class<T> type) {
    if (JSONArray.class.isAssignableFrom(type))
      return (T) this;
    if (Objects.equals(type, JSONValue[].class))
      return (T) toArray();
    return null;
  }

}

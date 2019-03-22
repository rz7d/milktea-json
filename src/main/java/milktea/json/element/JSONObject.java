package milktea.json.element;

public interface JSONObject extends JSONValue {

  @Override
  default boolean isObject() {
    return true;
  }

  JSONValue get(String name);

  default <T> T get(String name, Class<T> type) {
    var v = get(name);
    if (v == null)
      return null;
    return v.convert(type);
  }

  default JSONObject getObject(String name) {
    final JSONValue v = get(name);
    if (v.isObject())
      return (JSONObject) v;
    return null;
  }

  default JSONArray getArray(String name) {
    final JSONValue v = get(name);
    if (v.isArray())
      return (JSONArray) v;
    return null;
  }

  default JSONNumber getNumber(String name) {
    final JSONValue v = get(name);
    if (v.isNumber())
      return (JSONNumber) v;
    return null;
  }

  default String getString(String name) {
    final JSONValue v = get(name);
    if (v.isString())
      return ((JSONString) v).value();
    return null;
  }

  default boolean getBoolean(String name) {
    final JSONValue v = get(name);
    if (v.isBoolean())
      return ((JSONBoolean) v).value();
    throw new ClassCastException();
  }

  @Override
  default <T> T convert(Class<T> type) {
    if (JSONObject.class.isAssignableFrom(type))
      return type.cast(this);
    return null;
  }

}

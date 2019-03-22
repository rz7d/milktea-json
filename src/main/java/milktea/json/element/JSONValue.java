package milktea.json.element;

public interface JSONValue {

  default boolean isNull() {
    return false;
  }

  default boolean isObject() {
    return false;
  }

  default boolean isArray() {
    return false;
  }

  default boolean isNumber() {
    return false;
  }

  default boolean isString() {
    return false;
  }

  default boolean isBoolean() {
    return false;
  }

  <T> T convert(Class<T> type);

}

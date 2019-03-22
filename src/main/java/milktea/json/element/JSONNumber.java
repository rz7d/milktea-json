package milktea.json.element;

public interface JSONNumber extends JSONValue {

  @Override
  default boolean isNumber() {
    return true;
  }

  @Override
  @SuppressWarnings("unchecked")
  default <T> T convert(Class<T> type) {
    if (Number.class.isAssignableFrom(type))
      return (T) value();
    if (JSONNumber.class.isAssignableFrom(type))
      return (T) this;
    return null;
  }

  Number value();

}

package milktea.json.element;

public interface JSONString extends JSONValue {

    @Override
    default <T> T convert(Class<T> type) {
        if (String.class.isAssignableFrom(type))
            return type.cast(value());
        if (JSONString.class.isAssignableFrom(type))
            return type.cast(this);
        return null;
    }

    String value();

}

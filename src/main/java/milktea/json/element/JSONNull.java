package milktea.json.element;

import milktea.json.immutable.ImmutableJSONNull;

public interface JSONNull extends JSONValue {

    public static final JSONNull NULL = ImmutableJSONNull.NULL;

    @Override
    default boolean isNull() {
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    default <T> T convert(Class<T> type) {
        if (JSONNull.class.isAssignableFrom(type))
            return (T) this;
        return null;
    }

    default Object value() {
        return null;
    }

}

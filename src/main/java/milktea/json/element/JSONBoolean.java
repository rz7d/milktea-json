package milktea.json.element;

import milktea.json.immutable.ImmutableJSONBoolean;

public interface JSONBoolean extends JSONValue {

    JSONBoolean TRUE = ImmutableJSONBoolean.of(true);
    JSONBoolean FALSE = ImmutableJSONBoolean.of(false);

    @Override
    default <T> T convert(Class<T> type) {
        if (Boolean.class.isAssignableFrom(type))
            return type.cast(value());
        if (JSONBoolean.class.isAssignableFrom(type))
            return type.cast(this);
        return null;
    }

    boolean value();

}

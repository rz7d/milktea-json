package milktea.json.element;

import java.util.Optional;

public interface JSONObject extends JSONValue {

    JSONValue get(String name);

    default <T> Optional<T> get(String name, Class<T> type) {
        var v = get(name);
        if (v == null)
            return Optional.empty();
        return Optional.of(v.convert(type));
    }

    default String getString(String name) {
        return getJSONString(name).value();
    }

    default boolean getBoolean(String name) {
        return getJSONBoolean(name).value();
    }

    default JSONObject getJSONObject(String name) {
        return (JSONObject) get(name);
    }

    default JSONArray getJSONArray(String name) {
        return (JSONArray) get(name);
    }

    default JSONNumber getJSONNumber(String name) {
        return (JSONNumber) get(name);
    }

    default JSONString getJSONString(String name) {
        return (JSONString) get(name);
    }

    default JSONBoolean getJSONBoolean(String name) {
        return (JSONBoolean) get(name);
    }

    @Override
    default <T> T convert(Class<T> type) {
        if (JSONObject.class.isAssignableFrom(type))
            return type.cast(this);
        return null;
    }

}

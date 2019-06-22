package milktea.json.element;

import java.util.Objects;
import java.util.Optional;

public interface JSONArray extends JSONValue, Iterable<JSONValue> {

    int size();

    JSONValue get(int index);

    default JSONValue[] toArray() {
        final int size = size();
        JSONValue[] array = new JSONValue[size];
        for (int i = 0; i < size; ++i) {
            array[i] = get(i);
        }
        return array;
    }

    default <T> Optional<T> get(int index, Class<T> type) {
        var v = get(index);
        if (v == null)
            return Optional.empty();
        return Optional.of(v.convert(type));
    }

    default String getString(int index) {
        return getJSONString(index).value();
    }

    default boolean getBoolean(int index) {
        return getJSONBoolean(index).value();
    }

    default JSONObject getJSONObject(int index) {
        return (JSONObject) get(index);
    }

    default JSONArray getJSONArray(int index) {
        return (JSONArray) get(index);
    }

    default JSONNumber getJSONNumber(int index) {
        return (JSONNumber) get(index);
    }

    default JSONString getJSONString(int index) {
        return (JSONString) get(index);
    }

    default JSONBoolean getJSONBoolean(int index) {
        return (JSONBoolean) get(index);
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

package milktea.json.immutable;

import milktea.json.element.JSONArray;
import milktea.json.element.JSONValue;

import java.util.Arrays;
import java.util.Objects;

public class ImmutableJSONArray implements JSONArray {

    private final JSONValue[] elements;

    public ImmutableJSONArray(JSONValue[] elements) {
        this.elements = elements;
    }

    @Override
    public JSONValue[] toArray() {
        return Arrays.copyOf(elements, elements.length);
    }

    @Override
    public int length() {
        return elements.length;
    }

    @Override
    public JSONValue get(int index) {
        return elements[index];
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T convert(Class<T> type) {
        if (JSONArray.class.isAssignableFrom(type))
            return (T) this;
        if (Objects.equals(type, JSONValue[].class))
            return (T) elements;
        return null;
    }

}

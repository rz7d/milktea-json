package milktea.json.immutable;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import milktea.json.element.JSONArray;
import milktea.json.element.JSONValue;

public class ImmutableJSONArray implements JSONArray {

    private final List<JSONValue> elements;

    public ImmutableJSONArray(List<JSONValue> elements) {
        this.elements = elements;
    }

    @Override
    public JSONValue get(int index) {
        return elements.get(index);
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public JSONValue[] toArray() {
        return elements.toArray(new JSONValue[0]);
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

    @Override
    public Iterator<JSONValue> iterator() {
        return elements.iterator();
    }

}

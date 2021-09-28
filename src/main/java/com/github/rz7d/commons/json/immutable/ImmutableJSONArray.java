package com.github.rz7d.commons.json.immutable;

import com.github.rz7d.commons.json.model.JSONArray;
import com.github.rz7d.commons.json.model.JSONValue;

import java.util.*;
import java.util.stream.Stream;

public final class ImmutableJSONArray extends ImmutableJSONValue implements JSONArray {

    private static final ImmutableJSONArray EMPTY = new ImmutableJSONArray(Collections.emptyList());

    private static final class Builder implements JSONArray.Builder {

        private final ArrayList<JSONValue> elements = new ArrayList<>();

        Builder() {
        }

        @Override
        public void accept(JSONValue element) {
            elements.add(element);
        }

        public ImmutableJSONArray build() {
            if (elements.isEmpty())
                return EMPTY;
            return new ImmutableJSONArray(elements);
        }

    }

    public static ImmutableJSONArray empty() {
        return EMPTY;
    }

    public static ImmutableJSONArray of(List<JSONValue> value) {
        return new ImmutableJSONArray(value);
    }

    public static JSONArray.Builder builder() {
        return new Builder();
    }

    private final List<JSONValue> list;

    private ImmutableJSONArray(List<JSONValue> elements) {
        this.list = elements;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public JSONValue get(int index) {
        return list.get(index);
    }

    @Override
    public Stream<JSONValue> stream() {
        return list.stream();
    }

    @Override
    public Iterator<JSONValue> iterator() {
        return list.iterator();
    }

    @Override
    public Spliterator<JSONValue> spliterator() {
        return list.spliterator();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.list == null) ? 0 : this.list.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ImmutableJSONArray other = (ImmutableJSONArray) obj;
        if (this.list == null) {
            return other.list == null;
        } else {
            return this.list.equals(other.list);
        }
    }

}

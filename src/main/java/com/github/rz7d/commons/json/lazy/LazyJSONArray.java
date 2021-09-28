package com.github.rz7d.commons.json.lazy;

import com.github.rz7d.commons.json.lazy.parser.ValueToken;
import com.github.rz7d.commons.json.model.JSONArray;
import com.github.rz7d.commons.json.model.JSONValue;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Stream;

public final class LazyJSONArray implements JSONArray {

    private final List<ValueToken> list;

    public LazyJSONArray(List<ValueToken> list) {
        this.list = list;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public JSONValue get(int index) {
        ValueToken token = list.get(index);
        if (token == null)
            return null;
        return token.get();
    }

    @Override
    public Stream<JSONValue> stream() {
        return list.stream().map(ValueToken::get);
    }

    @Override
    public Iterator<JSONValue> iterator() {
        return stream().iterator();
    }

    @Override
    public Spliterator<JSONValue> spliterator() {
        return stream().spliterator();
    }

}

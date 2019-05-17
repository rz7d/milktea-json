package milktea.json.immutable;

import milktea.json.element.JSONString;

import java.util.WeakHashMap;

public class ImmutableJSONString implements JSONString {

    private static final WeakHashMap<String, ImmutableJSONString> CACHE = new WeakHashMap<>();

    public static ImmutableJSONString of(String value) {
        return CACHE.computeIfAbsent(value, ImmutableJSONString::new);
    }

    private final String value;

    private ImmutableJSONString(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

}

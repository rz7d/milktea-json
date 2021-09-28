package com.github.rz7d.commons.json.internal;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Function;

public final class Cache<K, V> {

    private final Function<? super K, ? extends V> initializer;

    private final Map<K, Reference<V>> map = new WeakHashMap<>();

    public Cache(Function<? super K, ? extends V> initializer) {
        this.initializer = initializer;
    }

    public Function<? super K, ? extends V> initializer() {
        return initializer;
    }

    public V get(K key) {
        V value = getOrNull(key);
        if (value == null) {
            put(key, value = initializer.apply(key));
        }
        return value;
    }

    public V getOrNull(K key) {
        Reference<V> reference = map.get(key);
        if (reference == null) {
            return null;
        }
        V value = reference.get();
        if (value == null) {
            map.remove(key);
        }
        return value;
    }

    public void put(K key, V value) {
        if (value != null) {
            map.put(key, new SoftReference<>(value));
        } else {
            map.remove(key);
        }
    }

    public void clear() {
        map.clear();
    }

}

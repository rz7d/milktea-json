package com.github.rz7d.commons.json.lazy.parser;

import java.nio.CharBuffer;

public abstract class Token<V> {

    protected final CharBuffer token;

    protected final Object lock = new Object();

    private volatile boolean validated;

    private volatile V cache;

    protected Token(CharBuffer token) {
        this.token = token;
    }

    public final V get() {
        if (!validated) {
            synchronized (lock) {
                if (!validated) {
                    cache = parse();
                    validated = true;
                }
            }
        }
        return cache;
    }

    protected abstract V parse();

}

package com.github.rz7d.commons.json.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public final class Iterables {

    private Iterables() {
    }

    public static boolean equals(Iterable<?> a, Iterable<?> b) {
        // FIXME: 順序が違うと false になる
        final Iterator<?> l = a.iterator();
        final Iterator<?> r = b.iterator();
        boolean equal = true;
        while (equal && l.hasNext() && r.hasNext()) {
            equal = Objects.equals(l.next(), r.next()) && l.hasNext() == r.hasNext();
        }
        return equal;
    }

}

package com.github.stannismod.lru;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class LRUHashMap<A, R> implements LRUCache<A, R> {

    private final Map<Integer, R> results = new HashMap<>();
    private final LinkedList<Integer> order = new LinkedList<>();

    private int size = 16;
    private Function<A, R> f;

    @Override
    public void setSize(final int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Size of cache can't be lower than 1");
        }

        this.size = size;
        if (order.size() <= this.size) {
            return;
        }
        order.subList(this.size, order.size()).forEach(results::remove);
        while (order.size() > this.size) order.removeLast();
    }

    @Override
    public void setFunction(final Function<A, R> f) {
        Objects.requireNonNull(f);
        this.f = f;
    }

    @Override
    public void clear() {
        results.clear();
        order.clear();
    }

    @Override
    public R get(final A arg) {
        Objects.requireNonNull(arg);

        Integer hash = arg.hashCode();
        R result;
        if (order.remove(hash)) {
            result = results.get(hash);
        } else {
            result = f.apply(arg);
            results.put(hash, result);
            setSize(size);
        }
        order.push(hash);
        return result;
    }
}

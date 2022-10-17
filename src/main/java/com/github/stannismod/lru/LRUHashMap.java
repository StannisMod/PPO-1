package com.github.stannismod.lru;

import java.util.*;
import java.util.function.Function;

public class LRUHashMap<A, R> implements LRUCache<A, R> {

    private final Map<Integer, R> results = new HashMap<>();
    private final LinkedList<Integer> order = new LinkedList<>();
    private int size = 16;

    @Override
    public void setSize(final int size) {
        this.size = size;
        if (order.size() <= this.size) {
            return;
        }
        order.subList(this.size, order.size()).forEach(results::remove);
        while (order.size() > this.size) order.removeLast();
    }

    @Override
    public void clear() {
        results.clear();
        order.clear();
    }

    @Override
    public R get(final A arg, final Function<A, R> f) {
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

package com.github.stannismod.lru;

import java.util.function.Function;

public interface LRUCache<A, R> {

    void setSize(int size);

    void clear();

    R get(A arg, Function<A, R> f);
}

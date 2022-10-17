package com.github.stannismod.lru;

import java.util.function.Function;

public interface LRUCache<A, R> {

    /**
     * Sets the actual caching size
     */
    void setSize(int size);

    /**
     * Sets the computing function of the cache
     @param f the function used to compute value
     */
    void setFunction(Function<A, R> f);

    /**
     * Clears the whole cache, now every {@code #get()} call should
     * directly compute function
     */
    void clear();

    /**
     * The main function of LRUCache. Should return value from cache,
     * if it's in, or compute it directly using {@code f}.
     * @param arg the argument to compute value
     * @return computed value
     */
    R get(A arg);
}

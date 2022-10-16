package com.github.stannismod.lru;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

public abstract class TestLRU extends Assertions {

    public <R> void testLRUAddWithoutOverflow(LRUCache<Integer, R> instance, Function<Integer, R> f) {
        instance.clear();
        int[] counter = new int[1];
        Function<Integer, R> sNormal = i -> { counter[0]++; return f.apply(i); };
        instance.setSize(2);
        assertEquals(f.apply(0), instance.get(0, sNormal));
        assertEquals(f.apply(1), instance.get(1, sNormal));
        assertEquals(2, counter[0]);
    }

    public <R> void testLRUAddWithOverflow(LRUCache<Integer, R> instance, Function<Integer, R> f) {
        instance.clear();
        Function<Integer, R> sErr = i -> { throw new IllegalStateException(); };
        instance.setSize(2);
        instance.get(0, f);
        instance.get(1, f);

        assertThrowsExactly(IllegalStateException.class, () -> instance.get(0, sErr));
        assertThrowsExactly(IllegalStateException.class, () -> instance.get(1, sErr));
    }

    public <R> void testLRU(LRUCache<Integer, R> instance, Function<Integer, R> f) {
        testLRUAddWithoutOverflow(instance, f);
        testLRUAddWithOverflow(instance, f);
    }
}

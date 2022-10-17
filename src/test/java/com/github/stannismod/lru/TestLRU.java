package com.github.stannismod.lru;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

public abstract class TestLRU<R> extends Assertions {

    private final LRUCache<Integer, R> instance;
    private final Function<Integer, R> f;
    private final Function<Integer, R> sNormal;
    private final Function<Integer, R> sErr;
    private int counter;

    public TestLRU(LRUCache<Integer, R> instance, Function<Integer, R> f) {
        this.instance = instance;
        this.f = f;
        this.sNormal = i -> { counter++; return f.apply(i); };
        this.sErr = i -> { throw new IllegalStateException(); };
    }

    @BeforeEach
    public void prepare() {
        instance.clear();
        counter = 0;
    }

    @Test
    public void testLRUAddWithoutOverflow() {
        instance.setSize(2);
        assertEquals(f.apply(0), instance.get(0, sNormal));
        assertEquals(f.apply(1), instance.get(1, sNormal));
        assertEquals(2, counter);
    }

    @Test
    public void testLRUAddWithOverflow() {
        instance.setSize(2);
        instance.get(0, f);
        instance.get(1, f);

        assertThrowsExactly(IllegalStateException.class, () -> instance.get(2, sErr));
        assertThrowsExactly(IllegalStateException.class, () -> instance.get(10, sErr));
    }

    @Test
    public void testLRUClear() {

    }
}

package com.github.stannismod.lru;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

public abstract class TestLRU<R> extends Assertions {

    private final LRUCache<Integer, R> instance;
    private final Function<Integer, R> f;
    private final Function<Integer, R> fNormal;
    private final Function<Integer, R> fErr;
    private int counter;

    public TestLRU(LRUCache<Integer, R> instance, Function<Integer, R> f) {
        this.instance = instance;
        this.f = f;
        this.fNormal = i -> { counter++; return f.apply(i); };
        this.fErr = i -> { throw new IllegalStateException(); };
    }

    @BeforeEach
    public void prepare() {
        instance.clear();
        counter = 0;
    }

    protected void fillWithTestData(int amount, boolean setSize) {
        if (setSize) {
            instance.setSize(amount);
        }
        for (int i = 0; i < amount; i++) {
            instance.get(i, f);
        }
    }

    protected void fillWithTestData(int amount) {
        fillWithTestData(amount, true);
    }

    @Test
    public void testLRUAddWithoutOverflow() {
        instance.setSize(2);
        assertEquals(f.apply(0), instance.get(0, fNormal));
        assertEquals(f.apply(1), instance.get(1, fNormal));
        assertEquals(2, counter);
    }

    @Test
    public void testLRUAddWithOverflow() {
        fillWithTestData(2);

        assertThrowsExactly(IllegalStateException.class, () -> instance.get(2, fErr));
        assertThrowsExactly(IllegalStateException.class, () -> instance.get(10, fErr));
    }

    @Test
    public void testLRUClear() {
        fillWithTestData(2);

        instance.clear();

        instance.get(0, fNormal);

        assertEquals(counter, 1);
    }
}

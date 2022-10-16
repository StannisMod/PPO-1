package com.github.stannismod.lru;

import org.junit.jupiter.api.Test;

public class TestLRUHashMap extends TestLRU {

    @Test
    public void testLRUHash() {
        testLRU(new LRUHashMap<>(), i -> i);
    }
}

package com.github.stannismod.lru;

public class TestLRUHashMap extends TestLRU<Integer> {

    public TestLRUHashMap() {
        super(new LRUHashMap<>(), i -> i);
    }
}

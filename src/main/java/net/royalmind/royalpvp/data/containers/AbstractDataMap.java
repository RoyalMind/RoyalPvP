package net.royalmind.royalpvp.data.containers;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractDataMap<K, V> {

    private final Map<K, V> dataMap = new HashMap<>();

    public void set(final K key, final V value) {
        this.dataMap.putIfAbsent(key, value);
    }

    public V get(final K key) {
        return this.dataMap.computeIfAbsent(key, k -> null);
    }

    public void update(final K key, final V value) {
        this.dataMap.put(key, value);
    }

    public void remove(final K key) {
        this.dataMap.remove(key);
    }
}

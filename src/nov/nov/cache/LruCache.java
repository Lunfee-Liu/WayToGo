package nov.nov.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LruCache<K, V> implements Cache<K, V> {
    private final Map<K, V> container;

    public LruCache(int capacity) {
        this.container = new LinkedHashMap<K, V>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > capacity;
            }
        };
    }

    @Override
    public V get(K key) {
        return container.get(key);
    }

    @Override
    public void put(K key, V value) {
        container.put(key, value);
    }
}

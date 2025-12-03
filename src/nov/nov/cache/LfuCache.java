package nov.nov.cache;

public class LfuCache<K, V> implements Cache<K, V> {
    private final int capacity;

    public LfuCache(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public void put(K key, V value) {

    }
}

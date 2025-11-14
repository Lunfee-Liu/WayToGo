package nov.cache;

public class Caches {
    public static <K, V> Cache<K, V> LRU (int capacity) {
        return new LruCache<>(capacity);
    }

    public static <K, V> Cache<K, V> LFU (int capacity) {
        return new LfuCache<>(capacity);
    }
}

package nov.nov.cache;

public class Main {
    public static void main(String[] args) {
        Cache<String, Integer> lruCache = Caches.LRU(9);
    }
}

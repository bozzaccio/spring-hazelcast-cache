package bozzaccio.poc.component;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import java.util.Objects;
import java.util.Set;

public abstract class CacheMap<K, V> {

    private final HazelcastInstance hazelcastInstance;
    private final String mapName;

    private IMap<K, V> cacheMap;

    public CacheMap(HazelcastInstance hazelcastInstance, String mapName) {
        this.hazelcastInstance = hazelcastInstance;
        this.mapName = mapName;
        this.cacheMap = hazelcastInstance.getMap(mapName);
    }

    public void putCacheData(K key, V value) throws IllegalArgumentException {

        Objects.requireNonNull(key, "key validation error");

        cacheMap = hazelcastInstance.getMap(mapName);

        try {
            cacheMap.put(key, value);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public V getCacheData(K key) throws IllegalArgumentException {

        Objects.requireNonNull(key, "key validation error");

        cacheMap = hazelcastInstance.getMap(mapName);
        try {
            return cacheMap.get(key);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("wrong key", e);
        }
    }

    public void removeCacheData(K key) {

        Objects.requireNonNull(key, "key validation error");

        cacheMap = hazelcastInstance.getMap(mapName);

        cacheMap.remove(key);
    }

    public Set<K> getKeySet() {

        if (Objects.nonNull(cacheMap)) {
            return cacheMap.keySet();
        } else {
            throw new NullPointerException("Cache map not valid");
        }
    }
}

package com.github.mgabr.demojobs.config;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RequestScopedCacheManager implements CacheManager {

    private static final ThreadLocal<Map<String, Cache>> threadLocalCache = ThreadLocal.withInitial(HashMap::new);

    @Override
    public Cache getCache(String name) {
        return threadLocalCache.get().computeIfAbsent(name, this::createCache);
    }

    private Cache createCache(String name) {
        return new ConcurrentMapCache(name);
    }

    @Override
    public Collection<String> getCacheNames() {
        return threadLocalCache.get().keySet();
    }

}

package com.example.spring_demo.provider.cache;

import java.util.Collection;

public interface CustomCacheManager {
    AbstractCustomCache getCache(String name);
    Collection<String> getCacheStorageNames();
}

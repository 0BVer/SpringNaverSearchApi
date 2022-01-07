package com.example.spring_demo.provider.cache;

import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

public class CurrentMapCustomCache extends AbstractCustomCache{

    private final String name;
    private final ConcurrentMap<String, Object> store;

    public CurrentMapCustomCache(String name, ConcurrentMap<String, Object> store) {
        this.name = name;
        this.store = store;
    }

    public String getName() {
        return name;
    }

    public Object getNativeCache(){
        return this.store;
    }

    @Override
    protected Optional<Object> lookup(Object key) {
        return Optional.ofNullable(this.store.get(key));
    }

    @Override
    public boolean put(String key, Object value) {
        this.store.put(key, value);
        return true; //TODO: 적합하지 않음
    }

    @Override
    public void evict(Object key) {

    }

    @Override
    public void clear() {

    }
}

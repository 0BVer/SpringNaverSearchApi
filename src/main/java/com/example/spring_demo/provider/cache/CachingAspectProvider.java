package com.example.spring_demo.provider.cache;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Aspect
@Component
public class CachingAspectProvider {
    private final CustomCacheManager cacheManager;

    public CachingAspectProvider(CustomCacheManager redisCustomCacheManager) { //or ServerMemmoryCustomCacheManager
        this.cacheManager = redisCustomCacheManager;
    }

    @Around("@annotation(com.example.spring_demo.provider.cache.LookAsideCaching) && @annotation(target)")
    public Object handlerLookAsideCaching(ProceedingJoinPoint joinPoint, LookAsideCaching target) throws Throwable {
        if (StringUtils.isEmpty(target.value())) {
            return joinPoint.proceed();
        }

        String cacheName = target.value();
        String cacheKey = getKey(joinPoint, target);

        return findInCaches(cacheName, cacheKey)
                .orElseGet(() -> {
                    try {
                        Object data = joinPoint.proceed();
                        putInCache(cacheName, cacheKey, data)
                                .thenAccept(isSave -> {
                                            if (isSave) log.info("__save__");
                                            else log.error("__get__");
                                        }
                                );
                        return data;
                    } catch (Throwable throwable) {
                        log.error("error...");
                        throw new RuntimeException("error...");
                    }
                });

    }

    private CompletableFuture<Boolean> putInCache(final String cacheName, final String cacheKey, final Object data) {
        return CompletableFuture.supplyAsync(() -> cacheManager.getCache(cacheName).put(cacheKey, data));
    }

    private String getKey(final ProceedingJoinPoint joinPoint, final LookAsideCaching target) {
        if ("NONE".equals(target.key()))
            return "simpleKey";
        else {
            CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
            int keyIndex = Arrays.asList(codeSignature.getParameterNames()).indexOf(target.key());
            return String.valueOf(joinPoint.getArgs()[keyIndex]);
        }
    }

    private Optional<Object> findInCaches(final String cacheName, final String cacheKey) {
        return cacheManager.getCache(cacheName).lookup(cacheKey);
    }
}

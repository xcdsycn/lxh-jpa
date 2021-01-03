package com.lxh.jpa.utils.norepeatsubmit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class UrlCache {

    @Bean
    public Cache<String,Integer> cache() {
        // 有效期2秒
        return  CacheBuilder.newBuilder().expireAfterWrite(2L, TimeUnit.SECONDS).build();
    }
}

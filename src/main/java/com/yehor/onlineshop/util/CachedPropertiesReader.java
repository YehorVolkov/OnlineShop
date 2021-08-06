package com.yehor.onlineshop.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CachedPropertiesReader {
    private final String path;

    private final Properties cachedProperties;

    public CachedPropertiesReader(String path) {
        this.path = path;
        cachedProperties = new Properties();
    }

    public Properties getCachedProperties() throws IOException {
        if (cachedProperties.isEmpty()) {
            try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path)) {
                cachedProperties.load(inputStream);
            }
        }
        return cachedProperties;
    }
}

package com.yehor.onlineshop.util;

import java.util.Properties;

public class CachedPropertiesReader {
    private final String path;

    private Properties cachedProperties;

    public CachedPropertiesReader(String path) {
        this.path = path;
        cachedProperties = readProperties();
    }

    public Properties getCachedProperties() {
        return new Properties(cachedProperties);
    }

    private Properties readProperties() {
        // readLogic
        this.cachedProperties = null;
        return null;
    }
}

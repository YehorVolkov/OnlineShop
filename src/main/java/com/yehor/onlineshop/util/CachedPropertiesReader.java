package com.yehor.onlineshop.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CachedPropertiesReader implements PropertiesReader {

    private final String path;
    private final Properties cachedProperties;

    public CachedPropertiesReader(String path) {
        this.path = path;
        cachedProperties = new Properties();
    }

    @Override
    public Properties getProperties() {
        if (cachedProperties.isEmpty()) {
            try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path)) {
                if (inputStream == null) {
                    throw new RuntimeException("Failed to open resources file");
                }
                cachedProperties.load(inputStream);
            } catch (IOException ioException) {
                throw new RuntimeException("Cannot read properties. Exception is: ", ioException);
            }
        }
        return cachedProperties;
    }
}

package com.teterin.mergesort.cmdline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class PropertiesLoader {

    private static final Logger log = LoggerFactory.getLogger(PropertiesLoader.class);
    private static final String PROPERTIES_FILE_NAME = "sort_options.properties";

    String getPropertyValue(String key) {
        String propertyValue = null;
        Properties instance = new Properties();
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME)) {
            if (inputStream != null) {

                instance.load(inputStream);
                propertyValue = instance.getProperty(key);
            } else {
                log.error("Не найден ключ '{}' в файле '{}'.", key, PROPERTIES_FILE_NAME);
            }
        } catch (IOException e) {
            log.error("Ошибка чтения из файла {}.", PROPERTIES_FILE_NAME, e);
        }
        return propertyValue;
    }
}

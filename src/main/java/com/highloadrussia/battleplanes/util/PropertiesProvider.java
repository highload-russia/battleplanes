package com.highloadrussia.battleplanes.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesProvider {

    private static final Map<String, String> propertiesCache = new HashMap<>();

    public static int getIntValue(String propertyName) {
        return Integer.parseInt(getStringValue(propertyName));
    }

    public static String getStringValue(String propertyName) {

        String propertyValue = propertiesCache.get(propertyName);

        if (propertyValue != null) {
            return propertyValue;
        }

        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "application.properties";

        Properties appProps = new Properties();

        try (InputStream input = new FileInputStream(appConfigPath)) {
            appProps.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        appProps.forEach((k, v) -> propertiesCache.put(k.toString(), v.toString()));

        return propertiesCache.get(propertyName);
    }
}
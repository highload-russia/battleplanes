package com.highloadrussia.battleplanes.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class PropertiesProvider {

    private static final String PROPERTIES_FILE_NAME = "application.properties";
    private static final Properties appProps = new Properties();

    static {
        String rootPath = Optional.ofNullable(Thread.currentThread()
                .getContextClassLoader()
                .getResource("")
        ).orElseThrow(() -> new RuntimeException("Cannot define path to game resources"))
                .getPath();

        try (InputStream input = new FileInputStream(rootPath + PROPERTIES_FILE_NAME)) {
            appProps.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Properties file reading error", e);
        }
    }

    public static int getIntValue(String propertyName) {
        return Integer.parseInt(appProps.getProperty(propertyName));
    }

    public static String getStringValue(String propertyName) {
        return appProps.getProperty(propertyName);
    }

    public static char getCharValue(String propertyName){
        return appProps.getProperty(propertyName).charAt(0);
    }
}
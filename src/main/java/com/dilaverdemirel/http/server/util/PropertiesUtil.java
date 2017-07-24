package com.dilaverdemirel.http.server.util;

import java.io.IOException;
import java.util.Properties;

/**
 * @author dilaverd on 7/21/2017.
 */
public class PropertiesUtil {
    private static Properties properties = new Properties();

    static {
        try {
            properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("simple-http-server.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key) {
        if( properties != null) {
            return properties.getProperty(key);
        } else {
            return null;
        }
    }
}

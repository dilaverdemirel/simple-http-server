package com.dilaverdemirel.http.server.util;

/**
 * @author dilaverd on 7/13/2017.
 */
public class StringUtils {
    public static String trim(String value){
        if(value != null){
            value = value.trim();
        }

        return value;
    }

    public static synchronized String concat(String... list){
        StringBuffer buffer = new StringBuffer();
        for (String item : list) {
            buffer.append(item);
        }

        return buffer.toString();
    }
}

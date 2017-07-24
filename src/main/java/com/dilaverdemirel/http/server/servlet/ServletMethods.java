package com.dilaverdemirel.http.server.servlet;

/**
 * @author dilaverd on 7/14/2017.
 */
public enum ServletMethods {
    GET("doGet"),POST("doPost");

    private String methodName;

    ServletMethods(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }
}

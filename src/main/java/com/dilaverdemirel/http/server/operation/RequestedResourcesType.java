package com.dilaverdemirel.http.server.operation;

/**
 * @author dilaverd on 7/14/2017.
 */
public enum RequestedResourcesType {
    STATIC("Static"),SERVLET("Servlet");

    private String name;

    RequestedResourcesType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

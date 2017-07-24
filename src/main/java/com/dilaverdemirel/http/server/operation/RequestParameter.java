package com.dilaverdemirel.http.server.operation;

/**
 * @author dilaverd on 7/24/2017.
 */
public class RequestParameter {
    private String name;
    private Object value;

    public RequestParameter() {
    }

    public RequestParameter(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}

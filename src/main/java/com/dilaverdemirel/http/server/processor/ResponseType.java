package com.dilaverdemirel.http.server.processor;

/**
 * @author dilaverd on 7/14/2017.
 */
public enum ResponseType {
    STATIC("Static",StaticResourceProcessor.class),SERVLET("Servlet",ServletResponseProcessor.class);

    private String name;
    private Class<ResponseProcessor> processor;

    ResponseType(String name,Class processor) {
        this.name = name;
        this.processor = processor;
    }

    public String getName() {
        return name;
    }

    public Class<ResponseProcessor> getProcessor() {
        return processor;
    }

    public static ResponseType find(String name){
        for (ResponseType responseType : ResponseType.values()) {
            if(responseType.name.equals(name)){
                return responseType;
            }
        }

        return null;
    }
}

package com.dilaverdemirel.http.server.processor;

import com.dilaverdemirel.http.server.operation.Response;

import java.io.OutputStream;

/**
 * @author dilaverd on 7/14/2017.
 */
public interface ResponseProcessor {
    public OutputStream createResponse(Response response);
}

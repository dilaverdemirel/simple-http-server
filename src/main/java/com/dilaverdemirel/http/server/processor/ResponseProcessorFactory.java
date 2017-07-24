package com.dilaverdemirel.http.server.processor;

import com.dilaverdemirel.http.server.operation.Request;
import com.dilaverdemirel.http.server.operation.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

/**
 * @author dilaverd on 7/14/2017.
 */
public class ResponseProcessorFactory {
    private static final Log logger = LogFactory.getLog(Response.class);

    public static Response createResponse(Request request) {
        OutputStream outputStream = new ByteArrayOutputStream();
        Response response = new Response(request, outputStream);

        try {
            ResponseType.find(request.getRequestedResourcesType().getName())
                    .getProcessor()
                    .newInstance()
                    .createResponse(response);

        } catch (InstantiationException e) {
            logger.error(e.getMessage(),e);
            throw new ResponseProcessingException(e.getMessage());
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(),e);
            throw new ResponseProcessingException(e.getMessage());
        }

        return response;
    }
}

package com.dilaverdemirel.http.server.processor;

import com.dilaverdemirel.http.server.TestData;
import com.dilaverdemirel.http.server.operation.Request;
import com.dilaverdemirel.http.server.operation.Response;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

/**
 * @author dilaverd on 7/14/2017.
 */
public class ResponseProcessorFactoryTest {
    @Test
    public void testServletResponse() throws MalformedURLException {
        InputStream inputStream = new ByteArrayInputStream(TestData.requestHeaderString.getBytes(StandardCharsets.UTF_8));
        Request request = new Request(TestData.getApplicationContext(),inputStream,  "Server Test");
        request.prepare();

        ResponseProcessorFactory factory = new ResponseProcessorFactory();
        Response response = factory.createResponse(request);
        Assert.assertEquals("Response Failed!",200,response.getStatus());
    }
}

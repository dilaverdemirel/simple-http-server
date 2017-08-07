package com.dilaverdemirel.http.server.operation;

import com.dilaverdemirel.http.server.TestData;
import com.dilaverdemirel.http.server.application.exception.ClassLoaderException;
import com.dilaverdemirel.http.server.application.exception.DocumentRootException;
import com.dilaverdemirel.http.server.application.webxml.WebXmlInitException;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author dilaverd on 7/12/2017.
 */
public class RequestTest {
    @Test
    public void testRequestStatus() throws DocumentRootException, WebXmlInitException, ClassLoaderException {
        InputStream inputStream = new ByteArrayInputStream(TestData.requestHeaderString.getBytes(StandardCharsets.UTF_8));
        Request request = new Request(TestData.getApplicationContext(),inputStream,  "Server Test");
        request.prepare();

        Assert.assertNotNull("Request failed!",request.getErrorMessage());
    }

    @Test
    public void testRequestStatusFailed() throws DocumentRootException, WebXmlInitException, ClassLoaderException {
        InputStream inputStream = new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));
        Request request = new Request(TestData.getApplicationContext(),inputStream,  "Server Test");
        request.prepare();

        Assert.assertEquals("Request failed!", 1, request.getErrorcode());
    }

    @Test
    public void testRequestHeaders() throws DocumentRootException, WebXmlInitException, ClassLoaderException {
        InputStream inputStream = new ByteArrayInputStream(TestData.requestHeaderString.getBytes(StandardCharsets.UTF_8));
        Request request = new Request(TestData.getApplicationContext(),inputStream,  "Server Test");
        request.prepare();

        Assert.assertNotNull("Method not found!",request.getMethod());
        Assert.assertNotNull("URI not found!",request.getURI());
        Assert.assertNotNull("Protocol not found!",request.getProtocol());
        Assert.assertNotNull("Hostname not found!",request.getHostname());
        Assert.assertNotNull("Query String not found!",request.getQueryString());
        Assert.assertFalse("Cookies not found!", request.getRequestCookies().isEmpty());

        Assert.assertNotNull(request.getRequestParameters());
        Assert.assertTrue(request.getRequestParameters().getParameters().size() == 3);
    }
}

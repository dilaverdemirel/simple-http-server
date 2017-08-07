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
 * @author dilaverd on 8/2/2017.
 */
public class SessionTest {
    @Test
    public void testRequestStatus() throws DocumentRootException, WebXmlInitException, ClassLoaderException {
        InputStream inputStream = new ByteArrayInputStream(TestData.requestHeaderString.getBytes(StandardCharsets.UTF_8));
        Request request = new Request(TestData.getApplicationContext(),inputStream,  "Server Test");
        request.prepare();

        Assert.assertNotNull("Request failed!", request.getErrorMessage());
    }
}

package com.dilaverdemirel.http.server.config;

import com.dilaverdemirel.http.server.TestData;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * @author dilaverd on 7/20/2017.
 */
public class ApplicationContextTest {

    @Test
    public void testCustomClassLoader() throws IOException, ClassNotFoundException {
        ApplicationContext applicationContext = TestData.getApplicationContext();
        Assert.assertNotNull("ClassLoader is null!",applicationContext.classLoader);
        Thread.currentThread().setContextClassLoader(applicationContext.classLoader);
        Class<?> testServlet = applicationContext.classLoader.loadClass("com.dilaverdemirel.http.server.sample.TestServlet");
        Class<?> servletFilter = applicationContext.classLoader.loadClass("javax.servlet.Filter");
        Assert.assertNotNull(testServlet);
        Assert.assertNotNull(servletFilter);
    }
}

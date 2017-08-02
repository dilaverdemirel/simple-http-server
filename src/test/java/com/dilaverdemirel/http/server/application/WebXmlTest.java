package com.dilaverdemirel.http.server.application;

import com.dilaverdemirel.http.server.TestData;
import com.dilaverdemirel.http.server.application.webxml.WebXmlInitException;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author dilaverd on 7/14/2017.
 */
public class WebXmlTest {
    @Test
    public void testInitWebXml() throws WebXmlInitException {
        ApplicationConfigManager configManager = new ApplicationConfigManager(TestData.testClassesDirectory);
        Assert.assertNotNull(configManager.webXml);
        Assert.assertTrue("Incorrect servlet count", configManager.webXml.getServletMappings().size() == 2);
        Assert.assertTrue("Incorrect context-param count", configManager.webXml.getContextParameters().size() == 1);
    }
}

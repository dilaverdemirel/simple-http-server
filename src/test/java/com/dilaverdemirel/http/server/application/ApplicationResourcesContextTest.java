package com.dilaverdemirel.http.server.application;

import com.dilaverdemirel.http.server.TestData;
import com.dilaverdemirel.http.server.util.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * @author dilaverd on 7/20/2017.
 */
public class ApplicationResourcesContextTest {

    @Test
    public void testGetResourcePaths() throws IOException {
        String dir = StringUtils.concat(File.separator,"resources-path-test-dir");
        ApplicationResourcesContext context = new ApplicationResourcesContext(TestData.testClassesDirectory);
        context.init();
        Set<String> resourcePaths = context.getResourcePaths(dir);
        Assert.assertTrue("Wrong resource count!",resourcePaths.size() == 2);
    }

    @Test
    public void testGetResourcePathsForRoot() throws IOException {
        String dir = File.separator;
        ApplicationResourcesContext context = new ApplicationResourcesContext(TestData.testClassesDirectory);
        context.init();
        Set<String> resourcePaths = context.getResourcePaths(dir);
        Assert.assertTrue("Wrong resource count!",resourcePaths.size() == 4);
    }
}

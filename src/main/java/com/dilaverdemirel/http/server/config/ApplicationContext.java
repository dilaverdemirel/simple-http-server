package com.dilaverdemirel.http.server.config;

import com.dilaverdemirel.http.server.application.ApplicationResourcesContext;
import com.dilaverdemirel.http.server.application.webxml.WebXml;
import com.dilaverdemirel.http.server.constant.Environment;
import com.dilaverdemirel.http.server.util.StreamUtils;
import com.dilaverdemirel.http.server.util.http.ContextParameterNamesEnumerator;
import com.dilaverdemirel.http.server.util.http.ServletContextHeaderNamesEnumerator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dilaverd on 7/18/2017.
 */
public class ApplicationContext implements ServletContext {
    private static final Log logger = LogFactory.getLog(ApplicationContext.class);

    protected String docRoot;

    protected ClassLoader classLoader;

    /**
     * The context attributes for this context.
     */
    protected Map<String,Object> attributes = new ConcurrentHashMap<String,Object>();

    protected ApplicationResourcesContext resourcesContext;

    protected String contextPath;

    protected WebXml webXml;

    public ApplicationContext(String docRoot) throws MalformedURLException {
        this.docRoot = docRoot;

        resourcesContext = new ApplicationResourcesContext(docRoot);
        try {
            resourcesContext.init();
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }

        ApplicationConfigManager configManager = new ApplicationConfigManager(docRoot);
        webXml = configManager.getWebXml();

        classLoader = configManager.initializeClassLoader();
    }

    @Override
    public String getContextPath() {
        return contextPath;
    }

    @Override
    public ServletContext getContext(String uri) {
        return this;
    }

    @Override
    public int getMajorVersion() {
        return Environment.MAJOR_VERSION;
    }

    @Override
    public int getMinorVersion() {
        return Environment.MAJOR_VERSION;
    }

    @Override
    public String getMimeType(String file) {
        return StreamUtils.getContentMimeType(file);
    }

    @Override
    public Set getResourcePaths(String path) {
        return resourcesContext.getResourcePaths(path);
    }

    @Override
    public URL getResource(String path) throws MalformedURLException {
        return resourcesContext.getResource(path);
    }

    @Override
    public InputStream getResourceAsStream(String path) {
        try {
            return resourcesContext.getResourceAsStream(path);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(),e);
        }

        return null;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String s) {
        //TODO : impl
        return null;
    }

    @Override
    public RequestDispatcher getNamedDispatcher(String s) {
        //TODO : impl
        return null;
    }

    /**
     * @deprecated As of Java Servlet API 2.1, with no direct replacement.
     */
    @Override
    @Deprecated
    public Servlet getServlet(String s) throws ServletException {
        return null;
    }

    /**
     * @deprecated As of Java Servlet API 2.1, with no direct replacement.
     */
    @Override
    @Deprecated
    public Enumeration<Servlet> getServlets() {
        return Collections.enumeration(Collections.EMPTY_LIST);
    }

    /**
     * @deprecated As of Java Servlet API 2.1, with no direct replacement.
     */
    @Override
    @Deprecated
    public Enumeration<String> getServletNames() {
        return Collections.enumeration(Collections.EMPTY_LIST);
    }

    @Override
    public void log(String message) {
        logger.info(message);
    }

    @Override
    public void log(Exception exception, String message) {
        logger.error(message,exception);
    }

    @Override
    public void log(String message, Throwable throwable) {
        logger.error(message,throwable);
    }

    @Override
    public String getRealPath(String path) {
        return resourcesContext.findResource(path).getFullPath();
    }

    @Override
    public String getServerInfo() {
        return Environment.SERVER_NAME;
    }

    @Override
    public String getInitParameter(String contextParamName) {
        return webXml
                .findContextParameter(contextParamName)
                .getParamName();
    }

    @Override
    public Enumeration getInitParameterNames() {
        return new ContextParameterNamesEnumerator(webXml.getContextParameters());
    }

    @Override
    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    @Override
    public Enumeration getAttributeNames() {
        return new ServletContextHeaderNamesEnumerator(attributes);
    }

    @Override
    public void setAttribute(String key, Object value) {
        attributes.put(key,value);
    }

    @Override
    public void removeAttribute(String key) {
        attributes.remove(key);
    }

    @Override
    public String getServletContextName() {
        return Environment.SERVER_NAME;
    }

    public String getDocRoot() {
        return docRoot;
    }

    public WebXml getWebXml() {
        return webXml;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }
}

package com.dilaverdemirel.http.server.application;

import com.dilaverdemirel.http.server.application.exception.ClassLoaderException;
import com.dilaverdemirel.http.server.application.webxml.WebXml;
import com.dilaverdemirel.http.server.application.webxml.WebXmlInitException;
import com.dilaverdemirel.http.server.processor.Connector;
import com.dilaverdemirel.http.server.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author dilaverd on 7/14/2017.
 */
public class ApplicationConfigManager {
    private static final Log logger = LogFactory.getLog(Connector.class);

    protected String docRoot;
    protected WebXml webXml;

    public ApplicationConfigManager(String docRoot) throws WebXmlInitException {
        this.docRoot = docRoot;
        initialize();
    }

    public void initialize() throws WebXmlInitException {
        initWebXml();
    }

    private void initWebXml() throws WebXmlInitException {
        try {
            File file = new File(StringUtils.concat(docRoot, "/WEB-INF/web.xml"));
            JAXBContext jaxbContext = JAXBContext.newInstance(WebXml.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            webXml = (WebXml) jaxbUnmarshaller.unmarshal(file);
        }catch (JAXBException e) {
            logger.error(e.getMessage(),e);
            throw new WebXmlInitException(e.getMessage());
        }
    }

    public ClassLoader initializeClassLoader() throws ClassLoaderException {
        try {
            File classesDirectory = new File(StringUtils.concat(docRoot, "/WEB-INF/classes/"));
            File libDirectory = new File(StringUtils.concat(docRoot, "/WEB-INF/lib/"));
            return URLClassLoader.newInstance(new URL[]{classesDirectory.toURI().toURL(), libDirectory.toURI().toURL()}, getClass().getClassLoader());
        }catch (Exception e){
            throw new ClassLoaderException(e.getMessage());
        }
    }

    public WebXml getWebXml() {
        return webXml;
    }
}

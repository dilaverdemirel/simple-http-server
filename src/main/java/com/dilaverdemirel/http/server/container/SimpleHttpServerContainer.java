package com.dilaverdemirel.http.server.container;

import com.dilaverdemirel.http.server.application.ApplicationContext;
import com.dilaverdemirel.http.server.application.BackgroundProcessor;
import com.dilaverdemirel.http.server.application.exception.ClassLoaderException;
import com.dilaverdemirel.http.server.application.exception.DocumentRootException;
import com.dilaverdemirel.http.server.application.webxml.WebXmlInitException;
import com.dilaverdemirel.http.server.constant.ConstantOfContainer;
import com.dilaverdemirel.http.server.util.PropertiesUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author dilaverd on 7/20/2017.
 */
public final class SimpleHttpServerContainer {
    private static final Log logger = LogFactory.getLog(SimpleHttpServerContainer.class);
    private static SimpleHttpServer server;

    protected static ApplicationContext applicationContext;

    public static void newInstance() throws DocumentRootException, ClassLoaderException, WebXmlInitException {
        BackgroundProcessor.init();

        applicationContext = new ApplicationContext(PropertiesUtil.getValue(ConstantOfContainer.DOC_ROOT_PROPERTY_NAME));
        server = new SimpleHttpServer(applicationContext);
        new Thread(server).start();

        while (true){}
    }

    public static void stopServer(){
        logger.info("Server stopping!");
        server.stop();
    }
}

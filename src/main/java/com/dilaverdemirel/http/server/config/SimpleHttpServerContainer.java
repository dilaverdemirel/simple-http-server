package com.dilaverdemirel.http.server.config;

import com.dilaverdemirel.http.server.SimpleHttpServer;
import com.dilaverdemirel.http.server.constant.ConstantOfContainer;
import com.dilaverdemirel.http.server.util.PropertiesUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.MalformedURLException;

/**
 * @author dilaverd on 7/20/2017.
 */
public class SimpleHttpServerContainer {
    private static final Log logger = LogFactory.getLog(SimpleHttpServerContainer.class);
    private static SimpleHttpServer server;

    protected static ApplicationContext applicationContext;
    public static void newInstance() throws MalformedURLException {
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

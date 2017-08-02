package com.dilaverdemirel.http.server;

import com.dilaverdemirel.http.server.application.webxml.WebXmlInitException;
import com.dilaverdemirel.http.server.application.exception.ClassLoaderException;
import com.dilaverdemirel.http.server.application.exception.DocumentRootException;
import com.dilaverdemirel.http.server.container.SimpleHttpServerContainer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author dilaverd on 7/5/2017.
 */
public class Starter {
    private static final Log logger = LogFactory.getLog(Starter.class);

    public static void main(String[] args) {
        try {
            SimpleHttpServerContainer.newInstance();
        } catch (DocumentRootException e) {
            e.printStackTrace();
        } catch (ClassLoaderException e) {
            e.printStackTrace();
        } catch (WebXmlInitException e) {
            e.printStackTrace();
        }
    }
}

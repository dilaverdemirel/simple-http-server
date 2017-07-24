package com.dilaverdemirel.http.server;

import com.dilaverdemirel.http.server.config.SimpleHttpServerContainer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.MalformedURLException;

/**
 * @author dilaverd on 7/5/2017.
 */
public class Starter {
    private static final Log logger = LogFactory.getLog(Starter.class);

    public static void main(String[] args) {
        try {
            SimpleHttpServerContainer.newInstance();
        } catch (MalformedURLException e) {
            logger.error(e.getMessage(),e);
        }
    }
}

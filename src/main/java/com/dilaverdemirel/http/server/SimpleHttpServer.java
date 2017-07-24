package com.dilaverdemirel.http.server;

import com.dilaverdemirel.http.server.config.ApplicationContext;
import com.dilaverdemirel.http.server.constant.ConstantOfContainer;
import com.dilaverdemirel.http.server.constant.Environment;
import com.dilaverdemirel.http.server.processor.Connector;
import com.dilaverdemirel.http.server.util.PropertiesUtil;
import com.dilaverdemirel.http.server.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author dilaverd on 7/5/2017.
 */
public class SimpleHttpServer implements Runnable {
    private static final Log logger = LogFactory.getLog(SimpleHttpServer.class);

    protected ApplicationContext applicationContext;

    private int serverPort = 8080;
    private ServerSocket serverSocket = null;
    private boolean isStopped = false;

    protected ExecutorService threadPool;

    public SimpleHttpServer(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        serverPort = Integer.parseInt(PropertiesUtil.getValue(ConstantOfContainer.SERVER_PORT_PROPERTY_NAME));
        threadPool = Executors.newFixedThreadPool(Integer.parseInt(PropertiesUtil.getValue(ConstantOfContainer.SERVER_THREAD_COUNT_PROPERTY_NAME)));
    }

    @Override
    public void run() {
        logger.info("Server Starting.");

        openServerSocket();
        logger.info("Server Started.");
        while (!isStopped()) {
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if (isStopped()) {
                    logger.info("Server Stopped.");
                    return;
                }
                throw new RuntimeException("Error accepting client connection", e);
            }

            this.threadPool.execute(new Connector(applicationContext,clientSocket, Environment.SERVER_NAME));
        }

        this.threadPool.shutdown();
        logger.info("Server Stopped.");
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop() {
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException(StringUtils.concat("Cannot open port ",String.valueOf(serverPort)), e);
        }
    }
}

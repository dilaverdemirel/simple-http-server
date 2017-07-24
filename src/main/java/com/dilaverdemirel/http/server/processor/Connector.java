package com.dilaverdemirel.http.server.processor;

import com.dilaverdemirel.http.server.config.ApplicationContext;
import com.dilaverdemirel.http.server.constant.ConstantOfHeader;
import com.dilaverdemirel.http.server.constant.Environment;
import com.dilaverdemirel.http.server.operation.Header;
import com.dilaverdemirel.http.server.operation.Request;
import com.dilaverdemirel.http.server.operation.Response;
import com.dilaverdemirel.http.server.util.StreamUtils;
import com.dilaverdemirel.http.server.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author dilaverd on 7/11/2017.
 */
public class Connector implements Runnable {
    private static final Log logger = LogFactory.getLog(Connector.class);

    protected ApplicationContext applicationContext;

    private Socket clientSocket = null;
    private String serverText = null;

    public Connector(ApplicationContext applicationContext,Socket clientSocket, String serverText) {
        this.applicationContext = applicationContext;
        this.clientSocket = clientSocket;
        this.serverText = serverText;
    }

    @Override
    public void run() {
        try(
                InputStream input = clientSocket.getInputStream();
                OutputStream output = clientSocket.getOutputStream();
        ) {
            Request request = new Request(applicationContext,input, serverText);
            request.prepare();

            if (request.getErrorcode() == 0) {
                Response response = ResponseProcessorFactory.createResponse(request);
                prepareHeaders(output, (ByteArrayOutputStream) response.getOutput(), response);
            } else {
                writeHttpStatus(output, request.getErrorcode());
                StreamUtils.writeDataToOutputStream(output, request.getErrorMessage());
            }

        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
    }

    private void prepareHeaders(OutputStream output, ByteArrayOutputStream outputStream, Response response) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Environment.DATE_PATTERN);

        response.getHeaderCollection().addHeader(new Header(ConstantOfHeader.SERVER, Environment.SERVER_NAME));
        int contentLength = ((ByteArrayOutputStream) outputStream).toByteArray().length;
        response.getHeaderCollection().addHeader(new Header(ConstantOfHeader.CONTENT_LENGTH, String.valueOf(contentLength)));
        response.getHeaderCollection().addHeader(new Header(ConstantOfHeader.CONTENT_TYPE, response.getContentType()));
        response.getHeaderCollection().addHeader(new Header(ConstantOfHeader.X_POWERED_BY, Environment.SERVER_NAME));
        response.getHeaderCollection().addHeader(new Header(ConstantOfHeader.DATE, dateFormat.format(new Date())));

        writeHttpStatus(output, response.getStatus());
        response.writeHeaders(output);
        output.write(((ByteArrayOutputStream)response.getOutput()).toByteArray());
    }

    private void writeHttpStatus(OutputStream output,int status) throws IOException {
        String statusKey = "OK";

        if(status != 200){
            statusKey = "ERROR";
        }

        StreamUtils.writeDataToOutputStream(output, StringUtils.concat(Environment.HTTP_VERSION, " ", String.valueOf(status), " ", statusKey, "\n"));
    }
}

package com.dilaverdemirel.http.server.operation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

/**
 * @author dilaverd on 7/11/2017.
 */
public class Response {
    private static final Log logger = LogFactory.getLog(Response.class);

    private OutputStream output;
    private Request request;
    private int status = 200;
    private String message;
    private String characterEncoding;
    private String contentType;
    private int contentLength;
    private boolean commited = true;
    private Locale locale;
    private HeaderCollection headerCollection = new HeaderCollection();

    public Response(Request request,OutputStream output) {
        this.output = output;
        setRequest(request);
    }

    public void writeHeaders(OutputStream output) throws IOException {
        headerCollection.getHeaders().forEach(header -> {
            try {
                output.write((header.getName() + ":" + header.getValue() + "\n").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        output.write(("\n").getBytes());
    }

    private void setRequest(Request request){
        this.request = request;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCharacterEncoding() {
        return characterEncoding;
    }

    public void setCharacterEncoding(String characterEncoding) {
        this.characterEncoding = characterEncoding;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public boolean isCommited() {
        return commited;
    }

    public void setCommited(boolean commited) {
        this.commited = commited;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public HeaderCollection getHeaderCollection() {
        return headerCollection;
    }

    public void setHeaderCollection(HeaderCollection headerCollection) {
        this.headerCollection = headerCollection;
    }

    public OutputStream getOutput() {
        return output;
    }

    public void setOutput(OutputStream output) {
        this.output = output;
    }

    public Request getRequest() {
        return request;
    }
}

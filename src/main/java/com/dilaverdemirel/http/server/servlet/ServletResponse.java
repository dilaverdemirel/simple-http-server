package com.dilaverdemirel.http.server.servlet;

import com.dilaverdemirel.http.server.constant.ConstantOfHeader;
import com.dilaverdemirel.http.server.constant.Environment;
import com.dilaverdemirel.http.server.operation.Header;
import com.dilaverdemirel.http.server.operation.Request;
import com.dilaverdemirel.http.server.operation.Response;
import com.dilaverdemirel.http.server.operation.SimpleCookie;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author dilaverd on 7/11/2017.
 */
public class ServletResponse implements HttpServletResponse {
    private static final Log logger = LogFactory.getLog(ServletResponse.class);

    private Request request;
    private Response simpleResponse;

    @Override
    public void addCookie(javax.servlet.http.Cookie cookie) {
        final StringBuffer sb = generateCookieString(cookie);
        simpleResponse.getCookies().put(cookie.getName(),sb.toString());
    }

    public StringBuffer generateCookieString(final javax.servlet.http.Cookie cookie) {
        final StringBuffer sb = new StringBuffer();

        SimpleCookie.appendCookieValue
                (sb, cookie.getVersion(), cookie.getName(), cookie.getValue(),
                        cookie.getPath(), cookie.getDomain(), cookie.getComment(),
                        cookie.getMaxAge(), cookie.getSecure(),
                        false);

        return sb;
    }

    @Override
    public boolean containsHeader(String name) {
        return simpleResponse.getHeaderCollection().contains(name);
    }

    @Override
    public String encodeURL(String url) {
        try {
            return URLEncoder.encode(url, Environment.ENCODING);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(),e);
        }

        return null;
    }

    @Override
    public String encodeRedirectURL(String url) {
        return encodeURL(url);
    }

    @Override
    public String encodeUrl(String url) {
        return encodeURL(url);
    }

    @Override
    public String encodeRedirectUrl(String url) {
        return encodeURL(url);
    }

    @Override
    public void sendError(int status, String message) throws IOException {
        simpleResponse.setStatus(status);
        simpleResponse.setMessage(message);

        // Clear any data content that has been buffered
        resetBuffer();
    }

    @Override
    public void sendError(int status) throws IOException {
        sendError(status, null);
    }

    @Override
    public void sendRedirect(String location) throws IOException {
        setHeader(ConstantOfHeader.LOCATION, location);
    }

    @Override
    public void setDateHeader(String name, long value) {
        SimpleDateFormat format = new SimpleDateFormat(ConstantOfHeader.HTTP_RESPONSE_DATE_HEADER,Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        addHeader(name,format.format(new Date(value)));
    }

    @Override
    public void addDateHeader(String name, long value) {
        setDateHeader(name, value);
    }

    @Override
    public void setHeader(String name, String value) {
        addHeader(name,value);
    }

    @Override
    public void addHeader(String name, String value) {
        simpleResponse.getHeaderCollection().addHeader(new Header(name, value));
    }

    @Override
    public void setIntHeader(String name, int value) {
        addHeader(name, "" + value);
    }

    @Override
    public void addIntHeader(String name, int value) {
        addHeader(name, "" + value);
    }

    @Override
    public void setStatus(int status) {
        simpleResponse.setStatus(status);
    }

    @Override
    public void setStatus(int status, String message) {
        simpleResponse.setStatus(status);
        simpleResponse.setMessage(message);
    }

    @Override
    public String getCharacterEncoding() {
        return simpleResponse.getCharacterEncoding();
    }

    @Override
    public String getContentType() {
        return simpleResponse.getContentType();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException {
                simpleResponse.getOutput().write(b);
            }
        };
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return new PrintWriter(simpleResponse.getOutput());
    }

    @Override
    public void setCharacterEncoding(String encoding) {
        simpleResponse.setCharacterEncoding(encoding);
    }

    @Override
    public void setContentLength(int length) {
        simpleResponse.setContentLength(length);
    }

    @Override
    public void setContentType(String contentType) {
        simpleResponse.setContentType(contentType);
    }

    @Override
    public void setBufferSize(int size) {
        //TODO : implements ServletResponse.setBufferSize
    }

    @Override
    public int getBufferSize() {
        //TODO : implements ServletResponse.getBufferSize
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {
        //TODO : implements ServletResponse.flushBuffer
    }

    @Override
    public void resetBuffer() {
        //TODO : implements ServletResponse.resetBuffer
    }

    @Override
    public boolean isCommitted() {
        return simpleResponse.isCommited();
    }

    @Override
    public void reset() {
        //TODO : implements ServletResponse.reset
    }

    @Override
    public void setLocale(Locale locale) {
        simpleResponse.setLocale(locale);
    }

    @Override
    public Locale getLocale() {
        return simpleResponse.getLocale();
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Response getSimpleResponse() {
        return simpleResponse;
    }

    public void setSimpleResponse(Response simpleResponse) {
        this.simpleResponse = simpleResponse;
    }
}

package com.dilaverdemirel.http.server.servlet;

import com.dilaverdemirel.http.server.constant.ConstantOfHeader;
import com.dilaverdemirel.http.server.operation.Request;
import com.dilaverdemirel.http.server.operation.SimpleCookie;
import com.dilaverdemirel.http.server.util.StringUtils;
import com.dilaverdemirel.http.server.util.http.HeaderNamesEnumerator;
import com.dilaverdemirel.http.server.util.http.HeaderValuesEnumerator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author dilaverd on 7/11/2017.
 */
public class ServletRequest implements HttpServletRequest {

    Request request;

    /**
     * Authentication type.
     */
    protected String authType = null;

    protected Cookie[] cookies = null;

    protected SimpleDateFormat formats[] = {
            new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US),
            new SimpleDateFormat("EEEEEE, dd-MMM-yy HH:mm:ss zzz", Locale.US),
            new SimpleDateFormat("EEE MMMM d HH:mm:ss yyyy", Locale.US)
    };

    @Override
    public String getAuthType() {
        return authType;
    }

    @Override
    public Cookie[] getCookies() {
        if(cookies == null) {
            List<SimpleCookie> serverCookies = request.getServerCookies();
            if (serverCookies != null) {
                cookies = new Cookie[serverCookies.size()];
                int i = 0;
                serverCookies.forEach(serverCookie -> {
                    cookies[i] = new Cookie(serverCookie.getName(), serverCookie.getValue());
                });
            }
        }
        return cookies;
    }

    @Override
    public long getDateHeader(String name) {
        String value = getHeader(name);
        if (value == null) {
            return (-1L);
        }

        Date date = null;
        for (int i = 0; i < formats.length ; i++) {
            try {
                date = formats[i].parse(value);
            } catch (ParseException e) {
                // Ignore
            }
        }

        if (date != null) {
            return date.getTime();
        }

        throw new IllegalArgumentException(value);
    }

    @Override
    public String getHeader(String name) {
        return request.getHeader(name);
    }

    @Override
    public Enumeration getHeaders(String name) {
        return new HeaderValuesEnumerator(request.getHeaderCollection(),name);
    }

    @Override
    public Enumeration getHeaderNames() {
        return new HeaderNamesEnumerator(request.getHeaderCollection());
    }

    @Override
    public int getIntHeader(String name) {
        return request.getIntHeader(name);
    }

    @Override
    public String getMethod() {
        return request.getMethod();
    }

    @Override
    public String getPathInfo() {
        return request.getPathInfo();
    }

    @Override
    public String getPathTranslated() {
        //TODO : implements ServletRequest.getPathTranslated
        return null;
    }

    @Override
    public String getContextPath() {
        return request.getContextPath();
    }

    @Override
    public String getQueryString() {
        return request.getQueryString();
    }

    @Override
    public String getRemoteUser() {
        //TODO : implements ServletRequest.getRemoteUser
        return null;
    }

    @Override
    public boolean isUserInRole(String s) {
        //TODO : implements ServletRequest.isUserInRole
        return false;
    }

    @Override
    public Principal getUserPrincipal() {
        //TODO : implements ServletRequest.getUserPrincipal
        return null;
    }

    @Override
    public String getRequestedSessionId() {
        return request.getHeader(ConstantOfHeader.DEFAULT_SESSION_COOKIE_NAME);
    }

    @Override
    public String getRequestURI() {
        return request.getURI();
    }

    @Override
    public StringBuffer getRequestURL() {
        return new StringBuffer(StringUtils.concat(request.getApplicationContext().getDocRoot(),request.getURI()));
    }

    @Override
    public String getServletPath() {
        return request.getServletPath();
    }

    @Override
    public HttpSession getSession(boolean b) {
        //TODO : implements ServletRequest.getSession
        return null;
    }

    @Override
    public HttpSession getSession() {
        //TODO : implements ServletRequest.getSession
        return null;
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        //TODO : implements ServletRequest.isRequestedSessionIdValid
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return request.getHeader(ConstantOfHeader.DEFAULT_SESSION_COOKIE_NAME) != null;
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        return getParameter("ConstantOfHeader.DEFAULT_SESSION_COOKIE_NAME") != null;
    }

    @Override
    public boolean isRequestedSessionIdFromUrl() {
        return getParameter("ConstantOfHeader.DEFAULT_SESSION_COOKIE_NAME") != null;
    }

    @Override
    public Object getAttribute(String s) {
        //TODO : implements ServletRequest.getAttribute
        return null;
    }

    @Override
    public Enumeration getAttributeNames() {
        //TODO : implements ServletRequest.getAttributeNames
        return null;
    }

    @Override
    public String getCharacterEncoding() {
        //TODO : implements ServletRequest.getCharacterEncoding
        return null;
    }

    @Override
    public void setCharacterEncoding(String s) throws UnsupportedEncodingException {
        //TODO : implements ServletRequest.setCharacterEncoding
    }

    @Override
    public int getContentLength() {
        //TODO : implements ServletRequest.getContentLength
        return 0;
    }

    @Override
    public String getContentType() {
        //TODO : implements ServletRequest.getContentMimeType
        return null;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return request.getInput().read();
            }
        };
    }

    @Override
    public String getParameter(String name) {
        return request.getRequestParameters().getRequestParameter(name);
    }

    @Override
    public Enumeration getParameterNames() {
        return request.getRequestParameters().getParameterNames();
    }

    @Override
    public String[] getParameterValues(String name) {
        return request.getRequestParameters().getParameterValues(name);
    }

    @Override
    public Map getParameterMap() {
        return request.getRequestParameters().getParameterMap();
    }

    @Override
    public String getProtocol() {
        return request.getProtocol();
    }

    @Override
    public String getScheme() {
        //TODO : implements ServletRequest.getScheme
        return null;
    }

    @Override
    public String getServerName() {
        //TODO : implements ServletRequest.getServerName
        return null;
    }

    @Override
    public int getServerPort() {
        //TODO : implements ServletRequest.getServerPort
        return 0;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        //TODO : implements ServletRequest.getReader
        return null;
    }

    @Override
    public String getRemoteAddr() {
        //TODO : implements ServletRequest.getRemoteAddr
        return null;
    }

    @Override
    public String getRemoteHost() {
        //TODO : implements ServletRequest.getRemoteHost
        return null;
    }

    @Override
    public void setAttribute(String s, Object o) {
        //TODO : implements ServletRequest.setAttribute
    }

    @Override
    public void removeAttribute(String s) {
        //TODO : implements ServletRequest.removeAttribute
    }

    @Override
    public Locale getLocale() {
        //TODO : implements ServletRequest.getLocale
        return new Locale("tr");
    }

    @Override
    public Enumeration getLocales() {
        //TODO : implements ServletRequest.getLocales
        return null;
    }

    @Override
    public boolean isSecure() {
        //TODO : implements ServletRequest.isSecure
        return false;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String s) {
        //TODO : implements ServletRequest.getRequestDispatcher
        return null;
    }

    @Override
    public String getRealPath(String s) {
        //TODO : implements ServletRequest.getRealPath
        return null;
    }

    @Override
    public int getRemotePort() {
        //TODO : implements ServletRequest.getRemotePort
        return 0;
    }

    @Override
    public String getLocalName() {
        //TODO : implements ServletRequest.getLocalName
        return null;
    }

    @Override
    public String getLocalAddr() {
        //TODO : implements ServletRequest.getLocalAddr
        return null;
    }

    @Override
    public int getLocalPort() {
        //TODO : implements ServletRequest.getLocalPort
        return 0;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}

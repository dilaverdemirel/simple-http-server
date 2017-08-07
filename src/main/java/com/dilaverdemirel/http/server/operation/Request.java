package com.dilaverdemirel.http.server.operation;

import com.dilaverdemirel.http.server.application.ApplicationContext;
import com.dilaverdemirel.http.server.application.webxml.ServletDefinition;
import com.dilaverdemirel.http.server.constant.ConstantOfHeader;
import com.dilaverdemirel.http.server.constant.Environment;
import com.dilaverdemirel.http.server.util.StreamReader;
import com.dilaverdemirel.http.server.util.StringUtils;

import java.io.InputStream;
import java.util.*;

/**
 * @author dilaverd on 7/5/2017.
 */
public class Request {
    protected ApplicationContext applicationContext;
    private String serverText = null;
    private String method;
    private String URI;
    private String protocol;
    private String hostname;
    private int errorcode = 0;
    private String queryString;
    private RequestedResourcesType requestedResourcesType;
    private String pathInfo;
    private ServletDefinition servletDefinition;
    private RequestParameters requestParameters;

    private InputStream input;
    private StringBuffer request = new StringBuffer(Environment.BUFFER_SIZE);
    private byte[] requestBuffer = new byte[Environment.BUFFER_SIZE];
    private List<SimpleCookie> requestCookies = new ArrayList<SimpleCookie>();
    private HeaderCollection headerCollection = new HeaderCollection();

    public Request(ApplicationContext applicationContext,InputStream input, String serverText) {
        this.applicationContext = applicationContext;
        this.input = input;
        this.serverText = serverText;
    }

    public void prepare() {
        new StreamReader(input,request).readRequest();
        headerCollection.parseHeaders(request.toString());
        prepareRequest();
    }

    public SimpleCookie findCookie(String name){
        return requestCookies.stream().filter(it -> it.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    private String prepareHeaderValue(String name) {
        Header header = headerCollection.findHeader(name);
        if (header != null) {
            return header.getValue();
        }

        return null;
    }

    private void prepareRequest() {
        setMethod(prepareHeaderValue(ConstantOfHeader.METHOD));
        setURI(prepareHeaderValue(ConstantOfHeader.URI));
        setProtocol(prepareHeaderValue(ConstantOfHeader.PROTOCOL));
        setHostname(prepareHeaderValue(ConstantOfHeader.HOST));
        setQueryString(prepareHeaderValue(ConstantOfHeader.QUERY_STRING));

        if(getURI() == null){
            setURI("");
        }

        detectServlet();

        if(servletDefinition == null){
            setRequestedResourcesType(RequestedResourcesType.STATIC);
        } else {
            setRequestedResourcesType(RequestedResourcesType.SERVLET);
        }

        prepareCookies();

        determineRequestStatus();

        requestParameters = new RequestParameters();
        requestParameters.setParameters(headerCollection.getRequestParameters());
    }

    private void detectServlet() {
        String[] splittedURI = getURI().split("\\?");

        servletDefinition = applicationContext.getWebXml().findServletByMapping(splittedURI[0]);
    }

    private void determineRequestStatus() {
        if (getMethod() == null) {
            errorcode = 1; //Could not extract Request Method
        }

        if (getURI() == null && errorcode == 0) {
            errorcode = 2; // Error extracting URI from Request
        }

        if (getProtocol() == null && errorcode == 0) {
            errorcode = 3; // Error in extracting Protocol Information
        }

        if (getHostname() == null && errorcode == 0) {
            errorcode = 4; // Error in extracting Hostname and Port
        }
    }

    private void prepareCookies() {
        Header cookieHeader = headerCollection.findHeader(ConstantOfHeader.COOKIE);
        if (cookieHeader != null) {
            if (cookieHeader.getValue() != null) {
                String[] pairs = cookieHeader.getValue().split(";");
                for (String pair : pairs) {
                    String[] keyValue = pair.split("=");
                    SimpleCookie serverCookie = new SimpleCookie();
                    serverCookie.setName(StringUtils.trim(keyValue[0]));
                    serverCookie.setValue(StringUtils.trim(keyValue[1]));
                    requestCookies.add(serverCookie);
                }
            }
        }
    }

    public String getErrorMessage() {
        String errormessage;
        switch (errorcode) {
            case 1:
                errormessage = "Parse Error 01 - Could not extract request Method";
                break;
            case 2:
                errormessage = "Parse Error 02 - Error extracting URI from Request";
                break;
            case 3:
                errormessage = "Parse Error 03 - Error extracting Protocol from Request";
                break;
            case 4:
                errormessage = "Parse Error 04 - Error extracting hostname and port";
                break;
            default:
                errormessage = "Parse Error 00 - Unkn own Error";
                break;
        }
        return errormessage;
    }

    public String getServerText() {
        return serverText;
    }

    public void setServerText(String serverText) {
        this.serverText = serverText;
    }

    public StringBuffer getRequest() {
        return request;
    }

    public void setRequest(StringBuffer request) {
        this.request = request;
    }

    public byte[] getRequestBuffer() {
        return requestBuffer;
    }

    public void setRequestBuffer(byte[] requestBuffer) {
        this.requestBuffer = requestBuffer;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public String getHeader(String name) {
        Header header = headerCollection.findHeader(name);
        return header != null ? header.getValue() : null;
    }

    public String getContextPath() {
        return applicationContext.getContextPath();
    }

    public InputStream getInput() {
        return input;
    }

    public void setInput(InputStream input) {
        this.input = input;
    }

    public List<SimpleCookie> getRequestCookies() {
        return requestCookies;
    }

    public void setRequestCookies(List<SimpleCookie> requestCookies) {
        this.requestCookies = requestCookies;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public RequestedResourcesType getRequestedResourcesType() {
        return requestedResourcesType;
    }

    public void setRequestedResourcesType(RequestedResourcesType requestedResourcesType) {
        this.requestedResourcesType = requestedResourcesType;
    }

    public HeaderCollection getHeaderCollection() {
        return headerCollection;
    }

    public void setHeaderCollection(HeaderCollection headerCollection) {
        this.headerCollection = headerCollection;
    }

    public int getIntHeader(String name) {
        Header header = headerCollection.findHeader(name);
        if(header != null) {
            return Integer.parseInt(header.getValue());
        } else {
            return -1;
        }
    }

    public String getPathInfo() {
        pathInfo = URI;
        return pathInfo;
    }

    public void setPathInfo(String pathInfo) {
        this.pathInfo = pathInfo;
    }

    public String getServletPath() {
        if(URI != null && URI.indexOf("?") != -1){
            return URI.substring(0,URI.indexOf("?"));
        }

        return null;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public ServletDefinition getServletDefinition() {
        return servletDefinition;
    }

    public RequestParameters getRequestParameters() {
        return requestParameters;
    }
}

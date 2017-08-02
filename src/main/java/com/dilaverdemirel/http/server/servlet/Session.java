package com.dilaverdemirel.http.server.servlet;

import com.dilaverdemirel.http.server.application.ApplicationContext;
import com.dilaverdemirel.http.server.operation.SimpleSession;
import com.dilaverdemirel.http.server.util.http.MapStringKeysEnumerator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;

/**
 * @author dilaverd on 8/2/2017.
 */
public class Session implements HttpSession {

    private SimpleSession simpleSession;
    private ApplicationContext applicationContext;

    public Session(SimpleSession simpleSession, ApplicationContext applicationContext) {
        this.simpleSession = simpleSession;
        this.applicationContext = applicationContext;
    }

    /**
     * Type array.
     */
    protected static final String EMPTY_ARRAY[] = new String[0];

    /**
     * Flag indicating whether this session is new or not.
     */
    protected boolean isNew = false;


    /**
     * Flag indicating whether this session is valid or not.
     */
    protected volatile boolean isValid = false;

    public Session(SimpleSession simpleSession) {
        this.simpleSession = simpleSession;
    }

    @Override
    public long getCreationTime() {
        return simpleSession.getCreationTime();
    }

    @Override
    public String getId() {
        return simpleSession.getSessionId();
    }

    @Override
    public long getLastAccessedTime() {
        return simpleSession.getLastAccessedTime();
    }

    @Override
    public ServletContext getServletContext() {
        return applicationContext;
    }

    @Override
    public void setMaxInactiveInterval(int i) {
        simpleSession.setSessionDuration(i);
    }

    @Override
    public int getMaxInactiveInterval() {
        return simpleSession.getSessionDuration();
    }

    @Override
    public HttpSessionContext getSessionContext() {
        return null;
    }

    @Override
    public Object getAttribute(String name) {
        return simpleSession.getParameters().get(name);
    }

    @Override
    public Object getValue(String name) {
        return getAttribute(name);
    }

    @Override
    public Enumeration getAttributeNames() {
        return new MapStringKeysEnumerator(simpleSession.getParameters());
    }

    @Override
    public String[] getValueNames() {
        return simpleSession.getParameters().keySet().toArray(EMPTY_ARRAY);
    }

    @Override
    public void setAttribute(String name, Object value) {
        simpleSession.getParameters().put(name,value);
    }

    @Override
    public void putValue(String name, Object value) {
        setAttribute(name,value);
    }

    @Override
    public void removeAttribute(String name) {
        simpleSession.getParameters().remove(name);
    }

    @Override
    public void removeValue(String name) {
        removeAttribute(name);
    }

    @Override
    public void invalidate() {
        applicationContext.getApplicationSessionManager().removeSession(simpleSession);
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}

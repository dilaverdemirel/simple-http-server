package com.dilaverdemirel.http.server.operation;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author dilaverd on 8/2/2017.
 */
public class SimpleSession implements Serializable{
    private String sessionId;
    private long lastActivityTime;
    private long creationTime;
    private long lastAccessedTime;
    private int sessionDuration = 30*60;
    private ConcurrentMap<String,Object> parameters;

    public SimpleSession(int sessionDuration, String sessionId) {
        this.sessionDuration = sessionDuration*60;
        this.sessionId = sessionId;

        parameters = new ConcurrentHashMap<>();
    }

    public String getSessionId() {
        return sessionId;
    }

    public long getLastActivityTime() {
        return lastActivityTime;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public int getSessionDuration() {
        return sessionDuration;
    }

    public void setSessionDuration(int sessionDuration) {
        this.sessionDuration = sessionDuration;
    }

    public long getLastAccessedTime() {
        return lastAccessedTime;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleSession that = (SimpleSession) o;

        if (sessionId != null ? !sessionId.equals(that.sessionId) : that.sessionId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return sessionId != null ? sessionId.hashCode() : 0;
    }
}

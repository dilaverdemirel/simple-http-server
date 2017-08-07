package com.dilaverdemirel.http.server.application;

import com.dilaverdemirel.http.server.operation.SimpleSession;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;


/**
 * @author dilaverd on 8/2/2017.
 */
public class ApplicationSessionManagerTest {
    @Test
    public void testGetSession() {
        ApplicationSessionManager sessionManager = new ApplicationSessionManager();
        SimpleSession session = sessionManager.getSession(null, true);
        org.junit.Assert.assertNotNull(session);
    }

    @Test
    public void testGetSessionWithSameId() {
        ApplicationSessionManager sessionManager = new ApplicationSessionManager();
        SimpleSession session = sessionManager.getSession(null, true);
        assertNotNull(session);

        session.getParameters().put("key", "value");

        SimpleSession session1 = sessionManager.getSession(session.getSessionId(), true);
        assertNotNull(session1);
        Object sessionParam = session1.getParameters().get("key");
        assertNotNull(sessionParam);
    }
}

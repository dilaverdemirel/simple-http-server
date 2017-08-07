package com.dilaverdemirel.http.server.application;

import com.dilaverdemirel.http.server.operation.SimpleSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author dilaverd on 8/2/2017.
 */
public class ApplicationSessionManager implements BackgroundProcess {
    private static final Log logger = LogFactory.getLog(ApplicationSessionManager.class);

    private ConcurrentMap<String,SimpleSession> sessions;

    public ApplicationSessionManager() {
        sessions = new ConcurrentHashMap<>();
    }

    public SimpleSession getSession(String sessionId,boolean create){
        SimpleSession session = null;

        if(sessionId != null) {
            session = sessions.get(sessionId);
        }

        if (session == null && create) {
            String jSessionId = UUID.randomUUID().toString();

            session = new SimpleSession(30, jSessionId);
            sessions.put(jSessionId, session);
        }

        return session;
    }

    public void removeSession(SimpleSession session){
        sessions.remove(session.getSessionId());
    }

    @Override
    public void process() {
        try{
            List<String> expiredSessionIds = new ArrayList<>();
            sessions.forEach((k,session) ->{
                long diff = System.currentTimeMillis() - session.getLastAccessedTime();
                int diffSecond = (int) (diff / 1000);
                if(diffSecond <= session.getSessionDuration()){
                    expiredSessionIds.add(session.getSessionId());
                }
            });

            expiredSessionIds.forEach(id -> sessions.remove(id));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }
}

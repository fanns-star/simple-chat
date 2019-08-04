package org.qianshan.chat.server;

import org.qianshan.chat.component.Session;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    private static final Map<String, Session> sessionMap = new ConcurrentHashMap<String, Session>();

    public static void addSession(Session session){
        sessionMap.put(session.getUserName(), session);
    }

    public static Session getSession(String userName){
        return sessionMap.get(userName);
    }

    public static void logout(String userName){
        Session session = sessionMap.get(userName);
        if (session != null){
            sessionMap.remove(userName);
            if (session.getChannel().isActive()){
                session.close();
            }
        }
    }

    public static void cleanSession(String userName){
        sessionMap.remove(userName);
    }

    public static Collection<Session> getSessionList(){
        return sessionMap.values();
    }
}

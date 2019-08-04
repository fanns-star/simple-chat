package org.qianshan.chat.server;

import org.qianshan.chat.component.Session;

import java.util.Collection;

public class ServerShutdownHook implements Runnable {
    public void run() {
        Collection<Session> sessions = SessionManager.getSessionList();
        for (Session session : sessions){
            if (session.getChannel().isActive()){
                session.close();
            }
        }
    }
}

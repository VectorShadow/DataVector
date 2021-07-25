package vsdl.datavector.link;

import vsdl.datavector.api.DataMessageHandler;
import vsdl.datavector.elements.DataMessage;

import java.net.Socket;
import java.util.HashMap;

public class LinkSessionManager {

    private final DataMessageHandler HANDLER;

    private final HashMap<Integer, LinkSession> SESSIONS;

    public LinkSessionManager(DataMessageHandler dmh) {
        HANDLER = dmh;
        SESSIONS = new HashMap<>();
    }

    public LinkSession getSessionByID(int id) {
        return SESSIONS.get(id);
    }

    public LinkSession addSession(Socket s) {
        LinkSession ls = new LinkSession(s, HANDLER);
        SESSIONS.put(ls.getId(), ls);
        return ls;
    }

    public void removeSession(int sessionID) {
        SESSIONS.remove(sessionID);
    }

    public void sendMessageOnSession(DataMessage dataMessage, int sessionID) {
        LinkSession ls = getSessionByID(sessionID);
        if (ls == null) {
            throw new IllegalArgumentException("No Session found with ID: " + sessionID);
        }
        ls.send(dataMessage);
    }

}

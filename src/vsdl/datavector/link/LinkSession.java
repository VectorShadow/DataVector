package vsdl.datavector.link;

import vsdl.datavector.api.DataMessageHandler;
import vsdl.datavector.elements.DataMessage;

import java.math.BigInteger;
import java.net.Socket;

public class LinkSession {

    private static int nextID = 1;

    private final DataLink LINK;

    private final DataMessageHandler HANDLER;

    private final int ID;

    private BigInteger sessionSecret = null;

    public LinkSession(Socket s, DataMessageHandler dmh) {
        LINK = new DataLink(s, this);
        HANDLER = dmh;
        ID = nextID++;
        LINK.start();
    }

    int getId() {
        return ID;
    }

    void messageReceived(DataMessage dm) {
        HANDLER.handle(dm, ID);
    }

    void error(Exception e) {
        HANDLER.handleDataLinkError(e, ID);
    }

    void close() {
        HANDLER.handleDataLinkClosure(ID);
    }

    public void send(DataMessage dataMessage) {
        LINK.transmit(dataMessage);
    }

    public BigInteger getSessionSecret() {
        return sessionSecret;
    }

    public void setSessionSecret(BigInteger bigInteger) {
        sessionSecret = bigInteger;
    }
}

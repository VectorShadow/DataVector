package vsdl.datavector.exec;

import vsdl.datavector.elements.DataMessage;
import vsdl.datavector.link.DataLink;

import java.io.IOException;
import java.net.Socket;

public class ClientDriver {
    private static final String HOSTNAME = "vps244728.vps.ovh.ca";

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(HOSTNAME, 31592);
        DataLink dataLink = new DataLink(socket, new TestDataMessageHandler());
        dataLink.start();
        dataLink.transmit(new DataMessage("{Hello world!}"));
    }
}

package vsdl.datavector.exec;

import vsdl.datavector.api.DataMessageHandler;
import vsdl.datavector.link.DataLink;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerDriver {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(31592);
        Socket socket;
        DataMessageHandler handler = new TestDataMessageHandler();
        for(;;) {
            try {
                socket = serverSocket.accept();
                System.out.println("Accepted new connection on " + socket.getLocalPort());
                DataLink dl = new DataLink(socket, handler);
                dl.start();
            } catch (IOException e) { //no need to kill the server here, log the error and continue
                System.out.println("Failed to accept connection");
            }
        }
    }
}

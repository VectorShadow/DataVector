package vsdl.datavector.link;

import vsdl.datavector.elements.DataMessage;

import static vsdl.datavector.transforms.ByteTransformer.*;

import java.io.IOException;
import java.net.Socket;

public class DataLink extends Thread {

    private static final int MAX_BUFFER = 4096;

    private final Socket SOCK;

    private boolean isActive = true;

    private String incompleteMessage = "";

    private final LinkSession LINK_SESSION;

    public DataLink(Socket s, LinkSession l) {
        SOCK = s;
        LINK_SESSION = l;
    }

    public void deactivate() {
        isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }

    public void listen() throws IOException {
        byte[] streamBuffer = new byte[MAX_BUFFER];
        int bytesReadFromStream = 0;
        do {
            //busywait
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new IllegalStateException("DataLink listener thread interrupted: " + e.getMessage());
            }
            //check for new data in the stream
            if (SOCK.getInputStream().available() == 0) continue;
            //read the new stream data into the stream buffer
            bytesReadFromStream = SOCK.getInputStream().read(streamBuffer);
            //process the new data
            processNewData(streamBuffer, bytesReadFromStream);
            //reset the stream buffer
            streamBuffer = new byte[MAX_BUFFER];
        } while (isActive);
        LINK_SESSION.close();
    }

    private void processNewData(byte[] data, int size) {
        char[] asChars = new char[size];
        for (int i = 0; i < size; ++i) {
            asChars[i] = toChar(data[i]);
        }
        StringBuilder messageBuilder = new StringBuilder();
        int dataIndex = 0;
        boolean openMessage = incompleteMessage.length() > 0;
        do {
            char c = asChars[dataIndex++];
            messageBuilder.append(c);
            if (c == DataMessage.HEADER) {
                if (openMessage) {
                    //stream error, two headers before a trailer. Dump all open messages as corrupt.
                    incompleteMessage = "";
                    messageBuilder = new StringBuilder();
                    LINK_SESSION.error(
                            new IllegalStateException("Stream error - multiple headers before trailer. Buffer dumped.")
                    );
                }
                //we now have an open message
                openMessage = true;
            } else if (c == DataMessage.TRAILER) {
                if (!openMessage) {
                    //stream error, trailer with no header. Dump all open messages as corrupt.
                    incompleteMessage = "";
                    LINK_SESSION.error(
                            new IllegalStateException("Stream error - trailer before header. Buffer dumped.")
                    );
                }
                //we can now close our open message
                openMessage = false;
                //handle receiving the completed DataMessage
                LINK_SESSION.messageReceived(new DataMessage(incompleteMessage + messageBuilder));
                //then reset inputs
                incompleteMessage = "";
                messageBuilder = new StringBuilder();
            } // else do nothing
        } while (dataIndex < size);
        //if we have an incomplete message, save it for the next stream read iteration
        if (messageBuilder.length() > 0) {
            incompleteMessage = messageBuilder.toString();
        }
    }

    public void transmit(DataMessage dataMessage) {
        try {
            SOCK.getOutputStream().write(dataMessage.getBytes());
        } catch (IOException e) {
            throw new IllegalStateException("DataLink transmit failed with IOException: " + e.getMessage());
        }
    }

    public void run() {
        try {
            listen();
        } catch (IOException e) {
            throw new IllegalStateException("IOException during listen thread: " + e.getMessage());
        }
    }
}

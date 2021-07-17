package vsdl.datavector.link;

import vsdl.datavector.api.DataMessageHandler;
import vsdl.datavector.elements.DataMessage;

import static vsdl.datavector.transforms.ByteTransformer.*;

import java.io.IOException;
import java.net.Socket;

public class DataLink extends Thread {

    private static final int MAX_BUFFER = 4096;

    private final Socket SOCK;
    private final DataMessageHandler HANDLER;

    private boolean isActive = true;

    private String incompleteMessage = "";

    public DataLink(Socket s, DataMessageHandler dmh) {
        SOCK = s;
        HANDLER = dmh;
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
        HANDLER.handleDataLinkClosure(this);
    }

    private void processNewData(byte[] data, int size) {
        char[] asChars = new char[size];
        for (int i = 0; i < size; ++i) {
            asChars[i] = toChar(data[i]);
        }
        StringBuilder messageBuilder = new StringBuilder();
        String partialMessage;
        DataMessage completeMessage;
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
                    HANDLER.handleDataLinkError(
                            new IllegalStateException("Stream error - multiple headers before trailer. Buffer dumped."),
                            this
                    );
                }
                //we now have an open message
                openMessage = true;
            } else if (c == DataMessage.TRAILER) {
                if (!openMessage) {
                    //stream error, trailer with no header. Dump all open messages as corrupt.
                    incompleteMessage = "";
                    messageBuilder = new StringBuilder();
                    HANDLER.handleDataLinkError(
                            new IllegalStateException("Stream error - trailer before header. Buffer dumped."),
                            this
                    );
                }
                //we can now close our open message
                openMessage = false;
                //then we create a DataMessage from any pre-existing incomplete message our current message
                completeMessage = new DataMessage(incompleteMessage + messageBuilder);
                //then reset both
                incompleteMessage = "";
                messageBuilder = new StringBuilder();
                //finally handle the completed DataMessage
                HANDLER.handle(completeMessage, this);
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

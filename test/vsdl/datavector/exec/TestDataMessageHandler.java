package vsdl.datavector.exec;

import vsdl.datavector.api.DataMessageHandler;
import vsdl.datavector.elements.DataMessage;
import vsdl.datavector.link.DataLink;

public class TestDataMessageHandler implements DataMessageHandler {

    @Override
    public void handle(DataMessage dataMessage, DataLink dataLink) {
        System.out.println(dataMessage.toString());
    }

    @Override
    public void handleDataLinkError(Exception e, DataLink dataLink) {
        System.out.println("Error - " + e.getClass() + ": " + e.getMessage());
    }

    @Override
    public void handleDataLinkClosure(DataLink dataLink) {
        System.out.println("DataLink closed.");
    }
}

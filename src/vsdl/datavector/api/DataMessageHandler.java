package vsdl.datavector.api;

import vsdl.datavector.elements.DataMessage;
import vsdl.datavector.link.DataLink;

public interface DataMessageHandler {
    void handle(DataMessage dataMessage, DataLink dataLink);
    void handleDataLinkError(Exception e, DataLink dataLink);
    void handleDataLinkClosure(DataLink dataLink);
}

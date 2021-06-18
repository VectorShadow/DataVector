package vsdl.datavector.api;

import vsdl.datavector.elements.DataMessage;
import vsdl.datavector.link.DataLink;

public interface DataMessageHandler {
    void handle(DataMessage dataMessage);
    void handleDataLinkError(Exception e);
    void handleDataLinkClosure(DataLink dl);
}

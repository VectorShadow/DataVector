package vsdl.datavector.api;

import vsdl.datavector.elements.DataMessage;
import vsdl.datavector.link.DataLink;

public interface DataMessageHandler {
    void handle(DataMessage dataMessage, int linkID);
    void handleDataLinkError(Exception e, int linkID);
    void handleDataLinkClosure(int linkID);
}

package vsdl.datavector.elements;

public class DataMessageBuilder {

    private StringBuilder messageText;

    private DataMessageBuilder(){
        messageText = new StringBuilder();
    }

    public static DataMessageBuilder start(String header) {
        DataMessageBuilder dmb = new DataMessageBuilder();
        dmb.messageText.append('{').append(header);
        return dmb;
    }

    public DataMessageBuilder addBlock(String blockText) {
        messageText.append('|').append(blockText);
        return this;
    }
    public DataMessage build() {
        return new DataMessage(messageText.append('}').toString());
    }
}

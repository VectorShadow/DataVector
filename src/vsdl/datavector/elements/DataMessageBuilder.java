package vsdl.datavector.elements;

import vsdl.datavector.crypto.CryptoUtilities;
import vsdl.datavector.crypto.Encryption;
import vsdl.datavector.link.LinkSession;

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

    public DataMessageBuilder addEncryptedBlock(String blockText, LinkSession encryptedSession) {
        return addBlock(
                CryptoUtilities.toAlphaNumeric(
                        Encryption.encryptDecrypt(
                                encryptedSession.getSessionSecret(),
                                CryptoUtilities.fromAlphaNumeric(blockText)
                        )
                )
        );
    }

    public DataMessage build() {
        return new DataMessage(messageText.append('}').toString());
    }
}

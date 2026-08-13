package client;

import javax.swing.text.Document;

public class Service {

    public Document readDocumentFromServer(){
        ClientForBackendServerStylepad client =
                new ClientForBackendServerStylepad("165.227.170.7", 7654);
        return client.readDocumentFromServer();
    }

    public boolean sendDocument(Document contentDocument) {
        ClientForBackendServerStylepad client =
                new ClientForBackendServerStylepad("165.227.170.7", 7654);
        if (client.getStateSocket()) {
            return client.sendDocument(contentDocument);
        }
        return false;

    }
}

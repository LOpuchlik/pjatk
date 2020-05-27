package middleserver;

import java.nio.channels.SelectionKey;

public class ClientSubscriber {
    public String topic;
    public SelectionKey selectionKey;
    public ClientSubscriber(String topic, SelectionKey selectionKey) {
        this.topic = topic;
        this.selectionKey = selectionKey;
    }
}

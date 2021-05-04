package middleserver;

import java.nio.channels.SelectionKey;

class ClientSubscriber {
    String topic;
    SelectionKey selectionKey;
    ClientSubscriber(String topic, SelectionKey selectionKey) {
        this.topic = topic;
        this.selectionKey = selectionKey;
    }
}

package zad1.middleserver;

import java.nio.channels.SelectionKey;

class TopicAndSelectionKeyObject {
    String topic;
    SelectionKey selectionKey;
    TopicAndSelectionKeyObject(String topic, SelectionKey selectionKey) {
        this.topic = topic;
        this.selectionKey = selectionKey;
    }
}

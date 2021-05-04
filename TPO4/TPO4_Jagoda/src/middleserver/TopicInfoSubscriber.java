package middleserver;

import java.nio.channels.SelectionKey;

class TopicInfoSubscriber {
    String userIdentity;
    SelectionKey selectionKey;
    TopicInfoSubscriber(String userIdentity, SelectionKey selectionKey) {
        this.userIdentity = userIdentity;
        this.selectionKey = selectionKey;
    }
}

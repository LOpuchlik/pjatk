package middleserver;

import java.nio.channels.SelectionKey;

public class TopicInfoSubscriber {
    String identification;
    SelectionKey selectionKey;
    TopicInfoSubscriber(String identification, SelectionKey selectionKey) {
        this.identification = identification;
        this.selectionKey = selectionKey;
    }
}

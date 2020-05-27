package middleserver;

import java.nio.channels.SelectionKey;

public class TopicInfoSubscriber {
    public String identification;
    public SelectionKey selectionKey;
    public TopicInfoSubscriber(String identification, SelectionKey selectionKey) {
        this.identification = identification;
        this.selectionKey = selectionKey;
    }
}

package zad1.middleserver;

import java.nio.channels.SelectionKey;

class UserAndSelectionKeyObject {
    String user;
    SelectionKey selectionKey;
    UserAndSelectionKeyObject(String user, SelectionKey selectionKey) {
        this.user = user;
        this.selectionKey = selectionKey;
    }
}

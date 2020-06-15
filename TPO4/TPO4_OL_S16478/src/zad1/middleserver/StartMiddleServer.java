package zad1.middleserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class StartMiddleServer {

    private static int port = 1025;
    private static String host = "localhost";
    private static Set<String> allTopics = new HashSet<>();
    private static Map<String, Set<TopicAndSelectionKeyObject>> topics_listsOfSubscribers = new HashMap<>();
    private static Map<String, String> topic_new = new HashMap<>();
    

    public static void main(String[] args) throws Exception {
        runMiddleServer();
    }

    private static void runMiddleServer() throws Exception {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();  // start servera
        System.out.println("server:\t\t\t\tis ready...");
        serverSocketChannel.bind(new InetSocketAddress(host, port));
        serverSocketChannel.configureBlocking(false);

        int serverValidOperations = serverSocketChannel.validOps();
        SelectionKey selectKey = serverSocketChannel.register(selector, serverValidOperations);

        while (true) {
            int numberOfKeys = selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectedKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();

                if (key.isAcceptable()) {

                    SocketChannel socketChannel = serverSocketChannel.accept(); // akceptacja polaczenia
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    System.out.println("server:\t\t\t\tAcceptable:\t\t\t\tnew connection\t\t\t\t\t" + socketChannel);

                } else if (key.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    String receivedMessage = readContent(socketChannel);
                    System.out.println("server:\t\t\t\tReadable:\t\t\t\treceived message\t\t\t\t" + receivedMessage);


// -------------- START ----------- COMMUNICATION WITH CLIENT -----------------------------------

                    if (receivedMessage.startsWith("subscribe:")) {
                        String[] subscriptionRequest = receivedMessage.split(":");
                        if (subscriptionRequest.length != 2) {
                        	System.out.println("Client wants to subscribe to empty or invalid topic!");
                        }

                        String topic = subscriptionRequest[1];
                        TopicAndSelectionKeyObject cS = new TopicAndSelectionKeyObject(topic, key);

                        Set<TopicAndSelectionKeyObject> subscribersSet;
                        if (topics_listsOfSubscribers.containsKey(topic)) {
                            subscribersSet = topics_listsOfSubscribers.get(topic);
                        } else {
                            subscribersSet = new HashSet<>();
                        }
                        subscribersSet.add(cS);
                        topics_listsOfSubscribers.put(topic, subscribersSet);

                    } else if (receivedMessage.startsWith("unsubscribe:")) { // when client wants to unsubscribe from topic
                        if (receivedMessage.length() == 12) {
                            System.err.println("You have to specify topic name from which you want to unsubscribe");
                            return;
                        }

                        String topic = receivedMessage.split(":")[1];
                        if (!topics_listsOfSubscribers.containsKey(topic)) {
                        	System.out.println("No topic - " + topic + " - in the topic-subscribers map");
                            return;
                        }
                        Set<TopicAndSelectionKeyObject> subscribers = topics_listsOfSubscribers.get(topic);
                        TopicAndSelectionKeyObject subscriberToRemove = null;
                        for (TopicAndSelectionKeyObject s: subscribers) {
                            if (s.selectionKey.equals(key)) {
                                subscriberToRemove = s;
                            }
                        }
                        if (subscriberToRemove == null) {
                            System.out.println("Client is not subscribed to the topic, thus cannot be removed");
                        } else {
                            subscribers.remove(subscriberToRemove);
                            topics_listsOfSubscribers.put(topic, subscribers);
                        }
                    } else if (receivedMessage.startsWith("topics")) {
                        UserAndSelectionKeyObject userAndSelectionKeyObject = new UserAndSelectionKeyObject("client", key);
                        socketChannel.register(selector, SelectionKey.OP_WRITE, userAndSelectionKeyObject);
                    }

// -------------- END -------------- COMMUNICATION WITH CLIENT -----------------------------------

// -------------- START ------------ COMMUNICATION WITH ADMIN -----------------------------------
                    if (receivedMessage.startsWith("news:")) {
                        forwardNewsToClient(receivedMessage, selector, key);
                    } else if (receivedMessage.startsWith("topics")) {
                        UserAndSelectionKeyObject userAndSelectionKeyObject = new UserAndSelectionKeyObject("admin", key);
                        socketChannel.register(selector, SelectionKey.OP_WRITE, userAndSelectionKeyObject);
                    } else if (receivedMessage.startsWith("add:")) {
                        addNewTopic(receivedMessage);
                    } else if (receivedMessage.startsWith("remove:")) {
                        topics_listsOfSubscribers.remove(receivedMessage.substring(7));
                        topic_new.remove(receivedMessage.substring(7));
                        allTopics.remove(receivedMessage.substring(7));
                    }

                } else if (key.isWritable()) {
                	System.out.println("server:\t\t\t\tWriteable");
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    String message = composeMessage(key);
                    byte[] messageInBytesForm = message.getBytes();
                    ByteBuffer byteBuffer = ByteBuffer.wrap(messageInBytesForm);
                    socketChannel.write(byteBuffer);
                    byteBuffer.clear();
                    socketChannel.register(selector, SelectionKey.OP_READ);

                } else if (key.isConnectable()) {
                	System.out.println("server:\t\t\t\tConnectable");
                }

                iterator.remove();
            }
        }
    }



    private static void addNewTopic(String output) {
        allTopics.add(output.substring(4));
    }

    private static String readContent(SocketChannel socketChannel) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        socketChannel.read(byteBuffer);
        return new String(byteBuffer.array()).trim();
    }

    private static String composeMessage(SelectionKey key) throws Exception {
        if (key.attachment() == null) {
            throw new Exception("Attachment is empty - null");
        }

        String m = "";
        if (key.attachment() instanceof TopicAndSelectionKeyObject) {
            String topic = ((TopicAndSelectionKeyObject) key.attachment()).topic;
            System.out.println("server:\t\t\t\tsent news on topic - " + topic);
            m = "news:" + topic + ":" + topic_new.get(topic);

        } else if (key.attachment() instanceof UserAndSelectionKeyObject) {
            if (((UserAndSelectionKeyObject) key.attachment()).user.equals("admin") || ((UserAndSelectionKeyObject) key.attachment()).user.equals("client")) {
                m = "topics: " + getTopicsSet();
            }
        }
        return m;
    }

    private static String getTopicsSet() {
        String topics="";
        for (Iterator<String> iterator = allTopics.iterator();
            iterator.hasNext(); ) {
            String it = iterator.next();
            topics += it + " ";
        }
        return topics;
    }

    private static void forwardNewsToClient(String receivedMessage, Selector selector, SelectionKey key) throws ClosedChannelException {
        String[] forwardedNewsOnTopic = receivedMessage.split(":");
        String topic = forwardedNewsOnTopic[1];
        String news = forwardedNewsOnTopic[2];
        												//     1    2      3
        if (forwardedNewsOnTopic.length != 3) { // format --> news:topic:tresc
            return;
        }
        if (!topics_listsOfSubscribers.containsKey(topic)) {
            System.out.println("Topic - " + topic + " - not present in the map/does not have any assigned subscribers");
            return;
        }

        // update map: topic-news
        System.out.println("server:\t\t\t\tUpdated news on: " + topic);
        topic_new.put(topic, news);

        Set<TopicAndSelectionKeyObject> subscribers = topics_listsOfSubscribers.get(topic);
        for (TopicAndSelectionKeyObject cS : subscribers) {
            SocketChannel socketChannel = (SocketChannel) cS.selectionKey.channel();
            socketChannel.register(selector, SelectionKey.OP_WRITE, cS);
            key.attach(cS);
        }
    }
}

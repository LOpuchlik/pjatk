package middleserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.rmi.server.ExportException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class StartMiddleServer {

    private static int port = 1025;
    private static Map<String, Set<ClientSubscriber>> topics_listsOfSubscribers = new HashMap<>();
    private static Map<String, String> topic_new = new HashMap<>();
    private static Set<String> allTopics = new HashSet<>();

    public static void main(String[] args) throws IOException {
        startMiddleServer();
    }

    private static void startMiddleServer() throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();  // start servera
        log("server:\t\t\t\tis ready...");
        serverSocketChannel.bind(new InetSocketAddress("localhost", port));
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

                    SocketChannel socket = serverSocketChannel.accept(); // akceptacja polaczenia
                    socket.configureBlocking(false);
                    socket.register(selector, SelectionKey.OP_READ);
                    log("server:\t\t\t\tAcceptable:\t\t\t\tnew connection\t\t\t\t\t" + socket);

                } else if (key.isReadable()) {
                    SocketChannel socket = (SocketChannel) key.channel();
                    String receivedMessage = readMessage(socket);  // receivedMessage = output
                    log("server:\t\t\t\tReadable:\t\t\t\treceived message\t\t\t\t" + receivedMessage);


// -------------- START ----------- COMMUNICATION WITH CLIENT -----------------------------------

                    if (receivedMessage.startsWith("subscribe:")) {
                        String[] subscriptionRequest = receivedMessage.split(":");
                        if (subscriptionRequest.length != 2) {
                            log("Client wants to subscribe to empty or invalid topic!");
                        }

                        String topic = subscriptionRequest[1];
                        ClientSubscriber cS = new ClientSubscriber(topic, key);

                        Set<ClientSubscriber> subscribersSet;
                        if (topics_listsOfSubscribers.containsKey(topic)) {
                            subscribersSet = topics_listsOfSubscribers.get(topic);
                        } else {
                            subscribersSet = new HashSet<>();
                        }
                        subscribersSet.add(cS);
                        topics_listsOfSubscribers.put(topic, subscribersSet);

                    } else if (receivedMessage.startsWith("unsubscribe:")) { // when client wants to unsubscribe from topic
                        if (receivedMessage.length() == 13) {
                            System.err.println("You have to specify topic name from which you want to unsubscribe");
                            return;
                        }

                        String topic = receivedMessage.split(":")[1];
                        if (!topics_listsOfSubscribers.containsKey(topic)) {
                            log("No topic - " + topic + " - in the topic-subscribers map");
                            return;
                        }
                        Set<ClientSubscriber> subscribers = topics_listsOfSubscribers.get(topic);
                        ClientSubscriber subscriberToRemove = null;
                        for (ClientSubscriber s: subscribers) {
                            if (s.selectionKey.equals(key)) {
                                //System.out.println("client unsubscribed from topic found");
                                subscriberToRemove = s;
                            }
                        }
                        if (subscriberToRemove == null) {
                            System.out.println("Client is not subscribed to the topic, thus cannot be removed");
                        } else {
                            subscribers.remove(subscriberToRemove);
                            topics_listsOfSubscribers.put(topic, subscribers);
                        }
                    } else if (receivedMessage.startsWith("TOPICS")) {
                        TopicInfoSubscriber topicInfoSubscriber = new TopicInfoSubscriber("client", key);
                        socket.register(selector, SelectionKey.OP_WRITE, topicInfoSubscriber);
                    }

// -------------- END -------------- COMMUNICATION WITH CLIENT -----------------------------------

// -------------- START ------------ COMMUNICATION WITH ADMIN -----------------------------------
                    if (receivedMessage.startsWith("NEWS:")) {
                        forwardNewsToClient(receivedMessage, selector, key);
                    } else if (receivedMessage.startsWith("TOPICS")) {
                        TopicInfoSubscriber topicInfoSubscriber = new TopicInfoSubscriber("admin", key);
                        socket.register(selector, SelectionKey.OP_WRITE, topicInfoSubscriber);
                    } else if (receivedMessage.startsWith("ADD:")) {
                        addNewTopic(receivedMessage);
                    } else if (receivedMessage.startsWith("REMOVE:")) {
                        topics_listsOfSubscribers.remove(receivedMessage.substring(7));
                        topic_new.remove(receivedMessage.substring(7));
                        allTopics.remove(receivedMessage.substring(7));
                    }

                } else if (key.isWritable()) {
                    log("server:\t\t\t\tWriteable");
                    SocketChannel socket = (SocketChannel) key.channel();
                    String message = composeMessage(key);
                    byte[] messageInBytesForm = message.getBytes();
                    ByteBuffer byteBuffer = ByteBuffer.wrap(messageInBytesForm);
                    socket.write(byteBuffer);
                    byteBuffer.clear();
                    socket.register(selector, SelectionKey.OP_READ);

                } else if (key.isConnectable()) {
                    log("server:\t\t\t\tConnectable");
                }

                iterator.remove();
            }
        }
    }



    private static void addNewTopic(String output) {
        allTopics.add(output.substring(4));
    }

    private static String readMessage(SocketChannel channel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(64);
        channel.read(buffer);
        return new String(buffer.array()).trim();
    }

    private static String composeMessage(SelectionKey key) throws ExportException {
        if (key.attachment() == null) {
            throw new ExportException("This shouldn't happen. Null attachment");
        }

        String mes = "";
        if (key.attachment() instanceof ClientSubscriber) {
            String topic = ((ClientSubscriber) key.attachment()).topic;
            System.out.println("server:\t\t\t\tsent news on topic - " + topic);
            mes = "NEWS:" + topic + ":" + topic_new.get(topic);

        } else if (key.attachment() instanceof TopicInfoSubscriber) {
            if (((TopicInfoSubscriber) key.attachment()).userIdentity.equals("admin") || ((TopicInfoSubscriber) key.attachment()).userIdentity.equals("client")) {
                mes = "TOPICS " + getTopicsSet();
            }
        }
        return mes;
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
        String[] newsForClientMessage = receivedMessage.split(":");
        String topic = newsForClientMessage[1];
        String news = newsForClientMessage[2];

        if (newsForClientMessage.length != 3) { // format to --> news:topic:tresc
            return;
        }
        if (!topics_listsOfSubscribers.containsKey(topic)) {
            System.out.println("Topic - " + topic + " - not in the map/does not have nay subscribers");
            return;
        }

        // update map: topic-news
        System.out.println("server:\t\t\t\tUpdated news on: " + topic);
        topic_new.put(topic, news);

        Set<ClientSubscriber> subscribers = topics_listsOfSubscribers.get(topic);
        for (ClientSubscriber cS : subscribers) {
            SocketChannel socket = (SocketChannel) cS.selectionKey.channel();
            socket.register(selector, SelectionKey.OP_WRITE, cS);
            key.attach(cS);
        }
    }

    private static void log(String logStatus) {
        System.out.println(logStatus);
    }
}

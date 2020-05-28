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
import java.util.LinkedList;
import java.util.List;
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
        log("Selector open: " + selector.isOpen());

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost", port));
        serverSocketChannel.configureBlocking(false);

        int ops = serverSocketChannel.validOps();
        SelectionKey selectKy = serverSocketChannel.register(selector, ops, null);

        while (true) {
            int numberOfKeys = selector.select();
            
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectedKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();

                if (key.isAcceptable()) {
                    log("Acceptable");

                    SocketChannel client = serverSocketChannel.accept();
                    client.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_READ);
                    log("Client: " + client + " accepted new connection");

                } else if (key.isReadable()) {
                    SocketChannel client = (SocketChannel) key.channel();

                    String output = readMessage(client);
                    log("Read from client: " + output);


                    /** Messages from client*/
                    if (output.startsWith("SUB:")) {
                        String[] subWithTopic = output.split(":");
                        if (subWithTopic.length != 2) {
                            log("Client wants to subscribe to empty or invalid topic!");
                        }

                        String topic = subWithTopic[1];
                        ClientSubscriber sub = new ClientSubscriber(topic, key);

                        Set<ClientSubscriber> subscribers;
                        if (topics_listsOfSubscribers.containsKey(topic)) {
                            subscribers = topics_listsOfSubscribers.get(topic);
                        } else {
                            subscribers = new HashSet<>();
                        }
                        subscribers.add(sub);
                        topics_listsOfSubscribers.put(topic, subscribers);

                    } else if (output.startsWith("UNSUB:")) {
                        handleClientUnsubscribeFromTopic(output, key);
                    } else if (output.startsWith("TOPICS")) {
                        TopicInfoSubscriber topicInfoSubscriber = new TopicInfoSubscriber("client", key);
                        client.register(selector, SelectionKey.OP_WRITE, topicInfoSubscriber);
                    }

                    /** Messages from admin*/
                    if (output.startsWith("NEWS:")) {
                        passNewsFromAdminToClients(output, selector, key);
                    } else if (output.startsWith("TOPICS")) {
                        TopicInfoSubscriber topicInfoSubscriber = new TopicInfoSubscriber("admin", key);
                        client.register(selector, SelectionKey.OP_WRITE, topicInfoSubscriber);
                    } else if (output.startsWith("REG:")) {
                        registerNewTopic(output);
                    } else if (output.startsWith("DEL:")) {
                        deleteExistingTopic(output);
                        deleteSubscribersOfTopic(output);
                        deleteNewsOnTopic(output);
                    }

                } else if (key.isWritable()) {
                    log("Writeable");

                    SocketChannel clientChannel = (SocketChannel) key.channel();

                    String msg = createMessageToSend(key);

                    byte[] message = msg.getBytes();
                    ByteBuffer buff = ByteBuffer.wrap(message);
                    clientChannel.write(buff);
                    buff.clear();

                    clientChannel.register(selector, SelectionKey.OP_READ);

                } else if (key.isConnectable()) {
                    log("Connectable");
                }

                iterator.remove();
            }
        }
    }

    private static void log(String s) {
        System.out.println(s);
    }

    private static void handleClientUnsubscribeFromTopic(String output, SelectionKey ky) {
        if (output.length() == 7) {
            log("Sending empty topic forbidden");
            return; // client send empty topic
        }

        String topic = output.split(":")[1];
        if (!topics_listsOfSubscribers.containsKey(topic)) {
            log("No key for: " + topic);
            return;
        }
        Set<ClientSubscriber> subscribers = topics_listsOfSubscribers.get(topic);
        ClientSubscriber subscriberToRemove = null;
        for (ClientSubscriber s: subscribers) {
            if (s.selectionKey.equals(ky)) {
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
    }

    private static void deleteExistingTopic(String output) {
        allTopics.remove(output.substring(4));
    }

    private static void deleteSubscribersOfTopic(String output) {
        topics_listsOfSubscribers.remove(output.substring(4));
    }

    private static void deleteNewsOnTopic(String output) {
        topic_new.remove(output.substring(4));
    }

    private static void registerNewTopic(String output) {
        allTopics.add(output.substring(4));
    }

    private static String readMessage(SocketChannel channel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(64);
        channel.read(buffer);
        return new String(buffer.array()).trim();
    }

    private static String createMessageToSend(SelectionKey ky) throws ExportException {
        if (ky.attachment() == null) {
            throw new ExportException("This shouldn't happen. Null attachment");
        }

        String msg = "";
        if (ky.attachment() instanceof ClientSubscriber) {

            String topic = ((ClientSubscriber) ky.attachment()).topic;
            System.out.println("Sending news on topic: " + topic);
            msg = "NEWS:" + topic + ":" + topic_new.get(topic);

        } else if (ky.attachment() instanceof TopicInfoSubscriber) {

            if (((TopicInfoSubscriber) ky.attachment()).identification.equals("admin")) {
                msg = "TOPICS " + getPossibleNewsTopics();
            } else {
                msg = "TOPICS " + getPossibleNewsTopics();
            }

        }

        return msg;
    }

    private static String getPossibleNewsTopics() {
        StringBuilder topics = new StringBuilder();
        for (Iterator<String> iterator = allTopics.iterator(); iterator.hasNext(); ) {
            String it = iterator.next();
            topics.append(it).append(" ");
        }
        return topics.toString();
    }

    private static void passNewsFromAdminToClients(String output, Selector selector, SelectionKey ky) throws ClosedChannelException {
        String[] topicWithNews = output.split(":");

        if (topicWithNews.length != 3) {
            System.out.println("Wrong length of message");
            return;
        }

        String topic = topicWithNews[1];
        String news = topicWithNews[2];

        if (!topics_listsOfSubscribers.containsKey(topic)) {
            System.out.println("Topic subscribers doesnt have key " + topic);
            return;
        }

        // update current news for topic
        System.out.println("Updating current news on topic " + topic);
        topic_new.put(topic, news);

        Set<ClientSubscriber> subscribers = topics_listsOfSubscribers.get(topic);
        for (ClientSubscriber clientSubscriber : subscribers) {
            SocketChannel cl = (SocketChannel) clientSubscriber.selectionKey.channel();
            cl.register(selector, SelectionKey.OP_WRITE, clientSubscriber);
            ky.attach(clientSubscriber);
        }
    }

}


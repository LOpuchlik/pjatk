package admin;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Admin {


    private AdminGuiHandler guiController;
    private SocketChannel client;
    private int port = 1025;

    public Admin(AdminGuiHandler adminGuiHandler) {
        this.guiController = adminGuiHandler;
    }

    public void startClient() throws IOException {
        Selector clientSelector = Selector.open();
        this.client = SocketChannel.open(new InetSocketAddress("localhost", port));
        this.client.configureBlocking(false);

        SelectionKey selectionKey = this.client.register(clientSelector, SelectionKey.OP_READ);

        while (true) {
            log("-  AdminGui "  + "is waiting for the select operation...");
            int availableChannels = clientSelector.select();
            if (availableChannels == 0)
                continue;

            Set<SelectionKey> selectedKeys = clientSelector.selectedKeys();
            Iterator<SelectionKey> iterator = selectedKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {

                } else if (key.isConnectable()) {

                } else if (key.isReadable()) {
                    SocketChannel serverChannel = (SocketChannel) key.channel();
                    String messageFromServer = readMessage(serverChannel);
                    log("Msg from server: " + messageFromServer);
                    handleReceivedMessage(messageFromServer);
                    serverChannel.register(clientSelector, SelectionKey.OP_READ);
                } else if (key.isWritable()) {

                }
                iterator.remove();
            }
        }
    }

    private String readMessage(SocketChannel channel) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        channel.read(byteBuffer);
        return new String(byteBuffer.array()).trim();
    }

    private void sendMessage(String msg) throws IOException {
        byte[] message = msg.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.wrap(message);
        client.write(byteBuffer);
        byteBuffer.clear();
    }

    void registerTopic(String topic) throws IOException {
        System.out.println("Registering new topic. Client:" + this.client);
        String message = "*REG_" + topic;
        log("Sending message " + message + " to client: " + client);
        sendMessage(message);
    }

    private void log(String s) {
        System.out.println(s);
    }


    void deregisterTopic(String topic) throws IOException {
        System.out.println("Deleting topic. Client:" + this.client);
        String message = "*DEL_" + topic;
        log("Sending message " + message + " to client: " + client);
        sendMessage(message);
    }

    void publishNews(String topicWithNews) throws IOException {
        System.out.println("Publishing news to topic. Client:" + this.client);
        String message = "*NEWS_" + topicWithNews;
        log("Sending message " + message + " to client: " + client);
        sendMessage(message);
    }

    public void refreshTopics() throws IOException {
        System.out.println("Refreshing topics. Client:" + this.client);
        String message = "*TOPICS";
        log("Sending message " + message + " to client: " + client);
        sendMessage(message);
    }

    private void handleReceivedMessage(String messageFromServer) {
        if (messageFromServer.startsWith("*TOPICS")) {
            this.guiController.handleRefreshedTopics(messageFromServer);
        }
    }
}

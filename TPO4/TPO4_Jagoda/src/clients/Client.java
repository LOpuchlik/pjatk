package clients;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;


public class Client {

    private CGui guiService;
    private SocketChannel client;
    private int port = 1025;

    public Client(CGui clientGuiHandler) {
        this.guiService = clientGuiHandler;
    }

    public void startClient() throws IOException {
        Selector clientSelector = Selector.open();
        this.client = SocketChannel.open(new InetSocketAddress("localhost", port));
        this.client.configureBlocking(false);

        SelectionKey selectionKey = this.client.register(clientSelector, SelectionKey.OP_READ);

        while (true) {
            log("*** " + this.getClass().getName() + " " + "Waiting for the select operation...");
            int readyChannels = clientSelector.select();
            if (readyChannels == 0)
                continue;

            Set<SelectionKey> selectedKeys = clientSelector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();

                if (key.isAcceptable()) {
                    log("*** " + this.getClass().getName() + " key is acceptable");

                } else if (key.isConnectable()) {
                    log("*** " + this.getClass().getName() + " key is connectable");

                } else if (key.isReadable()) {
                    log("*** " + this.getClass().getName() + " key is readable");

                    SocketChannel serverChannel = (SocketChannel) key.channel();

                    String messageFromServer = readMessage(serverChannel);
                    log("Message read from server: " + messageFromServer);

                    handleReceivedMessage(messageFromServer);

                    serverChannel.register(clientSelector, SelectionKey.OP_READ);

                } else if (key.isWritable()) {
                    log("*** " + this.getClass().getName() + " key is writable");
                }

                keyIterator.remove();
            }
        }
    }

    private void log(String s) {
        System.out.println(s);
    }

    private String readMessage(SocketChannel channel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(64);
        channel.read(buffer);
        return new String(buffer.array()).trim();
    }

    private void sendMessage(String msg) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        client.write(buffer);
        buffer.clear();
    }

    void subscribeToTopic(String topic) throws IOException {
        System.out.println("subscribing to topic. client:" + this.client);
        String message = new String("SUB:" + topic);
        log("Sending message " + message + " to client: " + client);
        sendMessage(message);
    }

    void unsubscribeFromTopic(String topic) throws IOException {
        System.out.println("unsubscribing from topic. client:" + this.client);
        String message = new String("UNSUB:" + topic);
        log("Sending message " + message + " to client: " + client);
        sendMessage(message);
    }

    void refreshTopics() throws IOException {
        System.out.println("Refreshing possible topics. client:" + this.client);
        String message = new String("TOPICS");
        log("Sending message " + message + " to client: " + client);
        sendMessage(message);
    }

    private void handleReceivedMessage(String messageFromServer) {
        if (messageFromServer.startsWith("NEWS:")) {
            this.guiService.displayNews(messageFromServer);
        } else if (messageFromServer.startsWith("TOPICS")) {
            this.guiService.displayListOfTopics(messageFromServer);
        }
    }
}
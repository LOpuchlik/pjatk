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

    private CGui cGui;
    private SocketChannel client;
    private int port = 1025;

    public Client(CGui cGui) {
        this.cGui = cGui;
    }

    public void startClient() throws IOException {
        Selector clientSelector = Selector.open();
        this.client = SocketChannel.open(new InetSocketAddress("localhost", port));
        this.client.configureBlocking(false);

        SelectionKey selectionKey = this.client.register(clientSelector, SelectionKey.OP_READ);

        while (true) {
            log("client:\t\t\t\twaiting for select");
            int readyChannels = clientSelector.select();
            if (readyChannels == 0)
                continue;

            Set<SelectionKey> selectedKeys = clientSelector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();

                if (key.isAcceptable()) {
                    log("client:\t\t\t\tAcceptable");

                } else if (key.isConnectable()) {
                    log("client:\t\t\t\tConnectable");

                } else if (key.isReadable()) {
                    log("client:\t\t\t\tReadable");
                    SocketChannel serverChannel = (SocketChannel) key.channel();
                    String messageFromServer = readMessage(serverChannel);
                    log("client:\t\t\t\tMessage read from server: " + messageFromServer);
                    if (messageFromServer.startsWith("NEWS:")) {
                        this.cGui.displayNews(messageFromServer);
                    } else if (messageFromServer.startsWith("TOPICS")) {
                        this.cGui.displayListOfTopics(messageFromServer);
                    }
                    serverChannel.register(clientSelector, SelectionKey.OP_READ);

                } else if (key.isWritable()) {
                    log("client:\t\t\t\tWritable");
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

    private void sendMessage(String message) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
        client.write(buffer);
        buffer.clear();
    }

    void subscribe(String topic) throws IOException {
        System.out.println("client:\t\t\t\tsubscribed to topic: " + topic);
        String message = new String("subscribe:" + topic);
        sendMessage(message);
    }

    void unsubscribe(String topic) throws IOException {
        System.out.println("client:\t\t\t\tUnsubscribed from: " + topic);
        String message = new String("unsubscribe:" + topic);
        sendMessage(message);
    }

    void refreshTopics() throws IOException {
        System.out.println("client:\t\t\t\trefreshed topics set");
        String message = new String("TOPICS");
        sendMessage(message);
    }

}
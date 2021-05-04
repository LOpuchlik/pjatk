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


    private AGui aGui;
    private SocketChannel client;
    private int port = 1025;

    /*  public Admin(AdminGuiHandler adminGuiHandler) {
          this.guiController = adminGuiHandler;
      }*/
    public Admin(AGui aGui) {
        this.aGui = aGui;
    }

    public void startClient() throws IOException {
        Selector clientSelector = Selector.open();
        this.client = SocketChannel.open(new InetSocketAddress("localhost", port));
        this.client.configureBlocking(false);

        SelectionKey selectionKey = this.client.register(clientSelector, SelectionKey.OP_READ);

        while (true) {
            log("admin:\t\t\t\twaiting for select");
            int availableChannels = clientSelector.select();
            if (availableChannels == 0)
                continue;

            Set<SelectionKey> selectedKeys = clientSelector.selectedKeys();
            Iterator<SelectionKey> iterator = selectedKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    log("admin:\t\t\t\tAcceptable");
                } else if (key.isConnectable()) {
                    log("admin:\t\t\t\tConnectable");
                } else if (key.isReadable()) {
                    log("admin:\t\t\t\tReadable");
                    SocketChannel serverChannel = (SocketChannel) key.channel();
                    String messageFromServer = readMessage(serverChannel);
                    log("admin:\t\t\t\tadmin to server:" + messageFromServer);
                    handleTopics(messageFromServer);
                    serverChannel.register(clientSelector, SelectionKey.OP_READ);
                } else if (key.isWritable()) {
                    log("admin:\t\t\t\tWritable");
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

    void addNewTopic(String topic) throws IOException {
        System.out.println("admin:\t\t\t\tnew topic added: " + topic);
        String message = "ADD:" + topic;
        sendMessage(message);
    }

    void removeTopic(String topic) throws IOException {
        System.out.println("admin:\t\t\t\tTopic - " + topic + " - removed");
        String message = "REMOVE:" + topic;
        sendMessage(message);
    }

    void sendNews(String topicWithNews) throws IOException {
        String topic = topicWithNews.split(":")[0];
        System.out.println("admin:\t\t\t\tmessage on topic - " + topic + " - sent");
        String message = "NEWS:" + topicWithNews;
        sendMessage(message);
    }

    public void refreshTopics() throws IOException {
        String message = "TOPICS";
        sendMessage(message);
    }

    private void handleTopics(String messageFromServer) {
        if (messageFromServer.startsWith("TOPICS")) {
            this.aGui.organizeTopics(messageFromServer);
        }
    }

    private void log(String logStatus) {
        System.out.println(logStatus);
    }
}
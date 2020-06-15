package zad1.admin;

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
    private SocketChannel adminChannel;
    private int port = 1025;
    private String host = "localhost";

    public Admin(AGui aGui) {
        this.aGui = aGui;
    }

    public void startClient() throws IOException {
        Selector adminSel = Selector.open();
        this.adminChannel = SocketChannel.open(new InetSocketAddress(host, port));
        this.adminChannel.configureBlocking(false);

        SelectionKey selectionKey = this.adminChannel.register(adminSel, SelectionKey.OP_READ);

        while (true) {
        	System.out.println("admin:\t\t\t\twaiting for select");
            int availableChannels = adminSel.select();
            if (availableChannels == 0)
                continue;

            Set<SelectionKey> selectedKeys = adminSel.selectedKeys();
            Iterator<SelectionKey> iterator = selectedKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                	System.out.println("admin:\t\t\t\tAcceptable");
                } else if (key.isConnectable()) {
                	System.out.println("admin:\t\t\t\tConnectable");
                } else if (key.isReadable()) {
                    System.out.println("admin:\t\t\t\tReadable");
                    SocketChannel serverChannel = (SocketChannel) key.channel();
                    String messageFromServer = readContent(serverChannel);
                    System.out.println("admin:\t\t\t\tadmin to server:" + messageFromServer);
                    handleTopics(messageFromServer);
                    serverChannel.register(adminSel, SelectionKey.OP_READ);
                } else if (key.isWritable()) {
                	System.out.println("admin:\t\t\t\tWritable");
                }
                iterator.remove();
            }
        }
    }

    void addNewTopic(String topic) throws IOException {
        System.out.println("admin:\t\t\t\tnew topic added: " + topic);
        String m = "add:" + topic;
        sendInfo(m);
    }

    void removeTopic(String topic) throws IOException {
        System.out.println("admin:\t\t\t\tTopic - " + topic + " - removed");
        String m = "remove:" + topic;
        sendInfo(m);
    }
    
    public void refreshTopics() throws IOException {
        sendInfo("topics");
    }
    
    private String readContent(SocketChannel socketChannel) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        socketChannel.read(byteBuffer);
        return new String(byteBuffer.array()).trim();
    }

    private void sendInfo(String m) throws IOException {
        byte[] message = m.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.wrap(message);
        adminChannel.write(byteBuffer);
        byteBuffer.clear();
    }

    void sendNews(String news) throws IOException {
        String topic = news.split(":")[0];
        System.out.println("admin:\t\t\t\tmessage on topic - " + topic + " - sent");
        String m = "news:" + news;
        sendInfo(m);
    }

    private void handleTopics(String messageFromServer) {
        if (messageFromServer.startsWith("topics")) {
            this.aGui.organizeTopics(messageFromServer);
        }
    }
}
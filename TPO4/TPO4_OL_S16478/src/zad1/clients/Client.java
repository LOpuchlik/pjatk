package zad1.clients;

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
    private SocketChannel clientChannel;
    private int port = 1025;
    private String host = "localhost";
   

    public Client(CGui cGui) {
        this.cGui = cGui;
    }

    public void startClient() throws IOException {
        Selector clientSel = Selector.open();
        this.clientChannel = SocketChannel.open(new InetSocketAddress(host, port));
        this.clientChannel.configureBlocking(false);

        SelectionKey selectionKey = this.clientChannel.register(clientSel, SelectionKey.OP_READ);

        while (true) {
        	System.out.println("client:\t\t\t\twaiting for select");
            int readyChannels = clientSel.select();
            if (readyChannels == 0)
                continue;

            Set<SelectionKey> selectedKeys = clientSel.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();

                if (key.isAcceptable()) {
                	System.out.println("client:\t\t\t\tAcceptable");

                } else if (key.isConnectable()) {
                	System.out.println("client:\t\t\t\tConnectable");

                } else if (key.isReadable()) {
                	System.out.println("client:\t\t\t\tReadable");
                    SocketChannel serverChannel = (SocketChannel) key.channel();
                    String messageFromServer = readContent(serverChannel);
                    System.out.println("client:\t\t\t\tMessage read from server: " + messageFromServer);
                    if (messageFromServer.startsWith("news:")) {
                        this.cGui.displayNews(messageFromServer);
                    } else if (messageFromServer.startsWith("topics")) {
                        this.cGui.displayListOfTopics(messageFromServer);
                    }
                    serverChannel.register(clientSel, SelectionKey.OP_READ);

                } else if (key.isWritable()) {
                	System.out.println("client:\t\t\t\tWritable");
                }
                keyIterator.remove();
            }
        }
    }

    void subscribe(String topic) throws IOException {
        System.out.println("client:\t\t\t\tsubscribed to topic: " + topic);
        String m = new String("subscribe:" + topic); // tworzy nowy obiekt a nie wrzuca do String poola
        sendRequest(m);
    }

    void unsubscribe(String topic) throws IOException {
        System.out.println("client:\t\t\t\tUnsubscribed from: " + topic);
        String m = new String("unsubscribe:" + topic);
        sendRequest(m);
    }

    void refreshTopics() throws IOException {
        System.out.println("client:\t\t\t\trefreshed topics set"); // topics are collected in a set, so there are no duplicates
        String m = new String("topics:");
        sendRequest(m);
    }
    
 // for sending to which topic client wants to subscribe or unsubscribe
    private void sendRequest(String m) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.wrap(m.getBytes());
        clientChannel.write(byteBuffer);
        byteBuffer.clear();
    }
  // for receiving updates on available topics and new on perticular topic  
    private String readContent(SocketChannel socketChannel) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        socketChannel.read(byteBuffer);
        return new String(byteBuffer.array()).trim();
    }



}
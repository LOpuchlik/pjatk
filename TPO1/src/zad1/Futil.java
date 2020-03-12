


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;



public class Futil {

    public static void processDir(String dirName, String resultFileName) {

        Charset cp1250 = Charset.forName("CP1250");
        Charset utf8 = StandardCharsets.UTF_8;

        Path destinationPath = Paths.get(resultFileName);
        try {
            destinationPath.toFile().createNewFile();
            FileChannel.open(destinationPath,StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING).close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            SimpleFileVisitor<Path> simpleFileVisitor = new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes bfa) throws IOException {

                    //System.out.println("Appending: " + path.toString());
                    if (!bfa.isRegularFile())
                        return CONTINUE;

                    FileChannel channelIn  = FileChannel.open(path);
                    FileChannel channelOut = FileChannel.open(destinationPath, StandardOpenOption.WRITE);
                    channelOut.position(channelOut.size());

                    ByteBuffer byteBufferIn = ByteBuffer.allocate(256);
                    int numberOfBytesRead = channelIn.read(byteBufferIn);

                    while (numberOfBytesRead != -1){
                        byteBufferIn.flip();
                        CharBuffer charBuffer = cp1250.decode(byteBufferIn);
                        ByteBuffer byteBufferOut = utf8.encode(charBuffer);
                        while (byteBufferOut.hasRemaining()){
                            channelOut.write(byteBufferOut);
                        }
                        byteBufferIn.clear();
                        numberOfBytesRead = channelIn.read(byteBufferIn);
                    }

                    channelIn.close();
                    channelOut.close();

                    return CONTINUE;

                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    exc.printStackTrace();
                    return CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    return CONTINUE;
                }
            };

            Files.walkFileTree(Paths.get(dirName), simpleFileVisitor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
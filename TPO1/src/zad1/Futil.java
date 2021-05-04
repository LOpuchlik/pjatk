


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;





public class Futil {

    public static void processDir(String dirName, String resultFileName) {


        Path targetPath = Paths.get(resultFileName);
		if(Files.exists(targetPath)) {
			FileChannel.open(targetPath, StandardOpenOption.WRITE).truncate(0).close();
		}

        try {
            SimpleFileVisitor<Path> simpleFileVisitor = new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

                   
                    FileChannel chi  = FileChannel.open(file);
                    FileChannel cho = FileChannel.open(targetPath, StandardOpenOption.WRITE);
				    ByteBuffer bbi = ByteBuffer.allocate(128);
                    
					
					cho.position(cho.size());
                    int bytesRead = chi.read(bbi);

                
               
                        while (bytesRead != -1){
                            bbi.flip();
                            CharBuffer chb = Charset.forName("CP1250").decode(bbi);
                            ByteBuffer bbo = Charset.forName("UTF-8").encode(chb);
                            while (bbo.hasRemaining()){
                                cho.write(bbo);
                            }
                            bbi.clear();
                            bytesRead = chi.read(bbi);
                        }

                        chi.close();
                        cho.close();
						

                    return FileVisitResult.CONTINUE;;

                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException e) throws IOException {
                    System.err.println(e);
                    return FileVisitResult.CONTINUE;;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
                    return FileVisitResult.CONTINUE;;
                }
            };

            Files.walkFileTree(Paths.get(dirName), simpleFileVisitor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
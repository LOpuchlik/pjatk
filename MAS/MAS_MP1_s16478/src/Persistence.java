import java.io.*;

public class Persistence {

    static void load() {
        if(new File("persistence.bin").isFile()) {
            try {
                // plik jest w folderze projektu
                FileInputStream fileInputStream = new FileInputStream("persistence.bin");
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                ObjectPlus.readExtents(objectInputStream);
                objectInputStream.close();
                fileInputStream.close();
            } catch(IOException e) {
                System.err.println(e);
            } catch(ClassNotFoundException ex) {
                System.err.println(ex + "\nClass has not been found.");
            }
        }
    }

    static void save() {
        try {
            // plik jest w folderze projektu
            FileOutputStream fileOutputStream = new FileOutputStream("persistence.bin");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            ObjectPlus.writeExtents(objectOutputStream);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch(IOException e) {
            System.err.println(e);
        }
    }

}

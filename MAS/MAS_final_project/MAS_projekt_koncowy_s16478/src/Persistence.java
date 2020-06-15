import java.io.*;

public class Persistence {

    private static String fileName = "persistence.txt";

    static void load() {
        if(new File(fileName).isFile()) {
            try {
                // plik jest w folderze projektu
                FileInputStream fileInputStream = new FileInputStream(fileName);
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
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            ObjectPlus.writeExtents(objectOutputStream);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch(IOException e) {
            System.err.println(e);
        }
    }

}
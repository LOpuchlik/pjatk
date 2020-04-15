import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class ObjectPlus implements Serializable {

    // mapa przechowująca wszystkie ekstensje (ekstensje wszystkich klas) - jest to taka tablica list
    private static Map<Class, List<ObjectPlus>> allExtents = new Hashtable<>(); // klucz - nazwa konkretnej biznesowej klasy
                                                                                // wartość - kontener zawierający referencje do jej wystąpień (właściwa ekstensja) - w tym przypadku Lista

    public ObjectPlus() {
        List<ObjectPlus> extent = null;
        Class theClass = this.getClass();

        if(allExtents.containsKey(theClass)) {
            extent = allExtents.get(theClass);
        } else {
            extent = new ArrayList<>();
            allExtents.put(theClass, extent);
        }
        extent.add(this);
    }

    //wyświetlenie ekstensji
    public static void showExtent(Class theClass) throws Exception {
        List<ObjectPlus> extent = null;

        if(allExtents.containsKey(theClass)) {  // Ekstensja tej klasy istnieje w kolekcji ekstensji
            extent = allExtents.get(theClass);
        } else {
            throw new Exception("Class: " + theClass + " does not exist!");
        }

        System.out.println("THE EXTENT OF " + theClass.getSimpleName() + " CLASS:\n");
        for(Object obj : extent)
            System.out.println(obj);
    }

    public static void writeExtents(ObjectOutputStream stream) throws IOException {
        stream.writeObject(allExtents);
    }

    public static void readExtents(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        allExtents = (Hashtable) stream.readObject();
    }


    public static <T> Iterable<T> getExtent(Class<T> type) throws ClassNotFoundException {
        if(allExtents.containsKey(type))
            return (Iterable<T>) allExtents.get(type);
        throw new ClassNotFoundException(String.format("%s. Stored extents: %s", type.toString(), allExtents.keySet()));
        }


}
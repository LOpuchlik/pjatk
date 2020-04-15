import com.sun.tools.javac.util.List;

import java.io.*;
import java.time.LocalDate;
import java.util.stream.StreamSupport;


public class Main {
    public static void main(String[] args) throws Exception {

        // do podpunktu trwałość ekstensji - po ponownym odpaleniu wczytuje się dane, które wcześniej zostały zapisane do pliku
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
                return;
            } catch(ClassNotFoundException ex) {
                System.err.println(ex + "\nClass has not been found.");
                return;
            }
        }
/*
  // przykładowe dane

        RegularEmployee e1 = new RegularEmployee("Jan", "Kowalski", LocalDate.of(1985, 10, 27), "e-mail: jan.kowalski@gmail.com");
        e1.setFormalEducation(List.of("Management (Harvard)"));
        e1.setHourlyRate(34.00);
        e1.setHoursWorkedInMonth(160);
        System.out.println("e1 salary: " + e1.getSalary());

        RegularEmployee e2 = new RegularEmployee("Anna", "Nowak", LocalDate.of(1994, 05, 15), "e-mail: anna.nowak@gmail.com");
        e2.setFormalEducation(List.of("Physics (University of Warsaw)", "Computer Science (PJATK)"));

        RegularEmployee e3 = new RegularEmployee("Adam", "Dobrowolski", LocalDate.of(1985, 06, 23), "e-mail: adam.dobrowolski@gmail.com");
        e3.setFormalEducation(List.of("Computer Science (PJATK)"));

        RegularEmployee e4 = new RegularEmployee("Dominik", "Długopolski", LocalDate.of(1983, 07, 7), "e-mail: dominik.dlugopolski@gmail.com");

        Manager m1 = new Manager("Michal", "Michalowski", LocalDate.of(1957, 12, 4), "email: michal.michalowski@gmail.com", 20000d);
        m1.setFormalEducation(List.of("Master of Business Administration (Oxford)", "Computer Science (Oxford)"));

        Manager m2 = new Manager("Ewa", "Nazaruk", LocalDate.of(1980, 10, 10), "email: ewa.nazaruk@gmail.com", 20000d);
        //

*/
        Iterable<RegularEmployee> employeeExtent = ObjectPlus.getExtent(RegularEmployee.class);
        for (RegularEmployee e : employeeExtent) {
            System.out.println(e.toString());
        }

        //System.out.println("Total number of Employees: " + (int) StreamSupport.stream(employeeExtent.spliterator(), false).count());
        //System.out.println("Mean RegularEmployee age: " + RegularEmployee.countMeanRegularEmployeesAge() + " years.");
        //System.out.println();

        Iterable<Manager> managerExtent = ObjectPlus.getExtent(Manager.class);
        for (Manager m : managerExtent) {
            System.out.println(m.toString());
        }


        // do podpunktu trwałość ekstensji - zapis danych wszystkich ekstensji do pliku persistence.bin; po ponownym odpaleniu będzie można wczytać dane(ta część IO na samej górze), które wcześniej zostały zapisane do pliku

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

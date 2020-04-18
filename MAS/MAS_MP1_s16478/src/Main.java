import com.sun.tools.javac.util.List;

import java.io.*;
import java.time.LocalDate;
import java.util.Map;



public class Main {
    public static void main(String[] args) throws Exception {

        // do podpunktu trwałość ekstensji - po ponownym odpaleniu wczytuje się dane, które wcześniej zostały zapisane do pliku
        //Persistence.load();

  // przykładowe dane

        RegularEmployee e1 = new RegularEmployee("Jan", "Kowalski", LocalDate.of(1985, 10, 27), "e-mail: jan.kowalski@gmail.com");
        e1.setFormalEducation(List.of("Management (Harvard)"));
        e1.setHourlyRate(34.00);
        e1.setHoursWorkedInMonth(160);
        //System.out.println("e1 salary: " + e1.getSalary());

        RegularEmployee e2 = new RegularEmployee("Anna", "Nowak", LocalDate.of(1994, 05, 15), "e-mail: anna.nowak@gmail.com");
        e2.setFormalEducation(List.of("Physics (University of Warsaw)", "Computer Science (PJATK)"));

        RegularEmployee e3 = new RegularEmployee("Adam", "Dobrowolski", LocalDate.of(1985, 06, 23), "e-mail: adam.dobrowolski@gmail.com");
        e3.setFormalEducation(List.of("Computer Science (PJATK)"));

        RegularEmployee e4 = new RegularEmployee("Dominik", "Długopolski", LocalDate.of(1983, 07, 7), "e-mail: dominik.dlugopolski@gmail.com");

        Manager m1 = new Manager("Michal", "Michalowski", LocalDate.of(1957, 12, 4), "email: michal.michalowski@gmail.com", 20000d);
        m1.setFormalEducation(List.of("Master of Business Administration (Oxford)", "Computer Science (Oxford)"));

        Manager m2 = new Manager("Ewa", "Nazaruk", LocalDate.of(1980, 10, 10), "email: ewa.nazaruk@gmail.com", 20000d);
        //



// drukowanie poszczeglnych ekstensji po nazwie klasy (ładny format)
        //ObjectPlus.showExtent(RegularEmployee.class);
        //ObjectPlus.showExtent(Manager.class);

// drukowanie wszystkich ekstensji na raz (brzydki format)
        //printAllExtents();

        //System.out.println("Total number of Employees: " + (int) StreamSupport.stream(employeeExtent.spliterator(), false).count());
        //System.out.println("Mean RegularEmployee age: " + RegularEmployee.countMeanRegularEmployeesAge() + " years.");
        //System.out.println();



        // do podpunktu trwałość ekstensji - zapis danych wszystkich ekstensji do pliku persistence.bin; po ponownym odpaleniu będzie można wczytać dane(ta część IO na samej górze), które wcześniej zostały zapisane do pliku
        //Persistence.save();
    }

    static void printAllExtents () {
        for (Map.Entry<Class, java.util.List<ObjectPlus>> entry : ObjectPlus.allExtents.entrySet()) {
            System.out.println("--> Key = " + entry.getKey() + "\n--> Value =\n" + entry.getValue());
            System.out.println();
        }
    }

}

import java.io.Serializable;
import java.time.LocalDate;

public class Manager extends RegularEmployee { // klasa Manager nie musi implementować Serializable, bo dzidziczy go z nadklasy RegularEmployee, która go implementuje


    private static double maxBonus = 5000d; // atrybut klasowy - wszyscy managerowie mogą mieć maksymalnie bonus tej wysokości
    private double managersBaseSalary; // atrybut obiektowy - każdy manager może mieć inną bazę, typ prymityeny - nie sprawdzam czy null
    private double percentBonus; // opcjonalny i obiektowy, z ograniczeniem (przyjmuje wartości od 0 do 1)


    public Manager(String name, String surname, LocalDate birthDate, String contactData, double managersBaseSalary) {
        super(name, surname, birthDate, contactData);
        this.managersBaseSalary = managersBaseSalary;
    }

    public static double getMaxBonus() {
        return maxBonus;
    }

    public static void setMaxBonus(double maxBonus) {
        Manager.maxBonus = maxBonus;
    }

    public double getBonus() {
        return percentBonus;
    }

    public void setBonus(double percentBonus) throws Exception {
        if(percentBonus < 0.0 || percentBonus > 1.0)
        {
            throw new Exception("Percent bonus has to be in range between 0 and 1");
        }
        this.percentBonus = percentBonus;
    }

    // przeciążanie metod, inaczej się liczy gdy nie ma bonusa i gdy jest bonus
    public double countSalary() {
        return managersBaseSalary;
    }

    public double countSalary(double percentBonus) {
        return managersBaseSalary + (percentBonus * maxBonus);
    }

    @Override   // przesłonięcie metody toString()
    public String toString() {
        String description = "";
        description += "Name and Surname:\t\t|\t" + getName() + " " + getSurname() + " (MANAGER)";
        description += "\nDate of Birth (age):\t|\t" + getBirthDate() + " (" + getAgeInYears() + " years old)";
        description += "\nContact Information:\t|\t" + getContactData();
        if(getFormalEducation().size() > 0)
            description += "\nFormal Education: \t\t|\t" + getFormalEducation().toString() + "\n";
        else
            description += "\nFormal Education: \t\t|\tNo formal education to show\n";

        return description;
    }
}

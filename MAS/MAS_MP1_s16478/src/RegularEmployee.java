import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

public class RegularEmployee extends ObjectPlus implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name; // wymagane
    private String surname; // wymagane
    private LocalDate birthDate; // wymagane
    private String contactData; // wymagane
    transient private int ageInYears; // wymagane i wyliczalne

    private List<String> formalEducation = new ArrayList<>(); // atrybut powtarzalny opcjonalny - lista przechowująca formalne wykształcenie pracownika (może być ich kilka, dlatego atrybut powtarzalny), nie może być puste, bo jakieś wykształcenie każdy ma
    private double hoursWorkedInMonth; // opcjonalne, nie dotyczy managerów
    private double hourlyRate; // opcjonalne - nie dotyczy managerów
    transient private double salary; // wyliczalne i opcjonalna, bo manager ma zryczałtowaną bazę (brak stawki godzinowej i liczby przepracowanych godzin - siedzi ile musi)



    public RegularEmployee(String name, String surname, LocalDate birthDate, String contactData) {
        super();  // konstruktor musi się odwoływac do nadklasy - ObjectPlus

        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.contactData = contactData;

        this.ageInYears = LocalDate.now().getYear() - birthDate.getYear();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null)
            throw new NullPointerException("Name is an obligatory field, cannot take null value");
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        if(surname == null)
            throw new NullPointerException("Surname is an obligatory field, cannot take null value");
        this.surname = surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        if(birthDate == null)
            throw new NullPointerException("Date of birth is an obligatory field, cannot take null value");
        this.birthDate = birthDate;
    }

    public String getContactData() {
        return contactData;
    }

    public void setContactData(String contactData) {
        if(contactData == null)
            throw new NullPointerException("Contact information is an obligatory field, cannot take null value");
        this.contactData = contactData;
    }

    public int getAgeInYears() {
        return ageInYears;
    }

    public void setAgeInYears(int ageInYears) {
        this.ageInYears = ageInYears;
    }

    public List<String> getFormalEducation() {
        return formalEducation;
    }

    public void setFormalEducation(List<String> input) {
        for (String s : input)
            this.formalEducation.add(s);
    }

    public double getHoursWorkedInMonth() {
        return hoursWorkedInMonth;
    }

    public void setHoursWorkedInMonth(double hoursWorkedInMonth) {
        this.hoursWorkedInMonth = hoursWorkedInMonth;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public double getSalary() {
        salary = hoursWorkedInMonth * hourlyRate;
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }


    @Override
    public String toString() {
        String description = "";
        description += "Name:\t\t\t\t\t|\t" + name;
        description += "\nSurname:\t\t\t\t|\t" + surname;
        description += "\nDate of Birth:\t\t\t|\t" + birthDate;
        description += "\nAge (in full years):\t|\t" + ageInYears;
        description += "\nContact Information:\t|\t" + contactData;
        if(formalEducation.size() > 0)
            description += "\nFormal Education: \t\t|\t" + getFormalEducation().toString() + "\n";
        else
            description += "\nFormal Education: \t\t|\tNo formal education to show\n";

        return description;
    }

    static double countMeanRegularEmployeesAge() throws ClassNotFoundException {
        Iterable<RegularEmployee> empls = ObjectPlus.getExtent(RegularEmployee.class);
        int numberOfEmployees = (int)StreamSupport.stream(empls.spliterator(), false).count();
        double sumOfAges = 0;
        for (RegularEmployee e : empls){
            sumOfAges += e.getAgeInYears();
        }
        return sumOfAges/numberOfEmployees;
    }

/*    public double countSalary() {
        salary = hoursWorkedInMonth * hourlyRate;
        return salary;
    }*/


}

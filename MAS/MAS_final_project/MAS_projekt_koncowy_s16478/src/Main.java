import com.sun.tools.javac.util.List;
import javafx.application.Application;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) throws Exception {

        //Persistence.load();

// checking correctness of regular inheritance between Project and ShortProject, MediumProject, LongProject classes
System.out.println("*************   TEST - ABSTRACT INHERITANCE Project, ShortProject, MediumProject, LongProject   ****************");
        ShortProject sp = new ShortProject("implementacja gui","implementacja interfejsu graficznego do obsługi.....", LocalDate.of(2020, 5, 30), LocalDate.of(2020, 6, 14), 15);
        //sp.setEndDate(LocalDate.of(2020,6 ,15));
        sp.setNumberOfFinishedTasks(13);

        MediumProject mp = new MediumProject("tworzenie bazy danych","istworzenie struktury bazy danych i zakodowanie logiki", LocalDate.of(2020, 5, 30), LocalDate.of(2020, 9, 30), 23);
        mp.setNumberOfFinishedTasks(3);

        LongProject lp = new LongProject("tworzenie logiki aplikacje","wykonanie projekowarchotektury systemu bla bla bla itd", LocalDate.of(2020, 5, 30), LocalDate.of(2021, 4, 30), 55);
        lp.setNumberOfFinishedTasks(6);
        System.out.println("-----------------------------------------------------------------");
        System.out.println(sp);
        System.out.println("-----------------------------------------------------------------");
        System.out.println(mp);
        System.out.println("-----------------------------------------------------------------");
        System.out.println(lp);
        System.out.println("-----------------------------------------------------------------");

        System.out.println();


//Checking correct formation of composition between Project and Task classes
System.out.println("*************   TEST - COMPOSITION BETWEEN PROJECT.class and TASK.class   ****************");
        Project p1 = new MediumProject("gra na iPhone'a", "napisanie w pelni funkcjonalnej gry platformowej na iPhone'a", LocalDate.of(2020,6,13), LocalDate.of(2020,12,10), 70);
        Project p2 = new ShortProject("to-do list", "napisanie prostego programu na androida pozwalajacego planowac rzeczy,ktore sa danego dnia do zrobienia", LocalDate.of(2020,6,02), LocalDate.of(2020,8,10), 15);


        Task t1 = Task.createTask(p1,  "logowanie uzytkownika", "oprogramowanie gui do logowania użytkownika - okienko, dwa pola tekstowe, dwa przyciski, sprawdzanie poprawnosci loginu i hasla", Priority.low);
        Task t2 = Task.createTask(p1,  "rozwijany widget typu menu", "oprogramowanie gui do wyboru przez użytkownika opcji - nowej gry, zaladowania poprzednio zapisanej gry, zapisania biezacej gry lub wyjscia z gry", Priority.medium);
        Task t3 = Task.createTask(p2,  "okno główne", "okno główne ma pozwalać na dodawanie nowych zadań do zrobienia danego dnia i umożliwiać zaznaczanie/odznaczanie zrobionego zadania", Priority.high);
        Task t4 = Task.createTask(p2, "dodawanie tagow", "implementacja dodawania kategorii do każdego z wprowadzanych zadan w celu łatwego wyszukiwania oraz do użycia w pozniejszym etapie w module statystyk", Priority.medium);
        Task t5 = Task.createTask(p2, "moduł statystyk", "implementacja modułu statystyk, pozwalająca podejrzeć, które zadania są wykonwane powtarzalnie itp.", Priority.low);

        p1.setNumberOfFinishedTasks(1);
        p1.addTask(t1);
        p1.addTask(t2);


        p2.setNumberOfFinishedTasks(1);
        p2.addTask(t3);
        p2.addTask(t4);
        p2.addTask(t5);
        //p2.addTask(t2);  // t2 jest juz czescia p1 - nie mozna dodac :D


        System.out.println("Projekt 1");
        System.out.println(p1.toString());
        System.out.println();
        System.out.println("Projekt 2");
        System.out.println(p2.toString());

        System.out.println();

// TEST - OVERLAPPING BETWEEN REGULAR EMPLOYEE AND LEADER
System.out.println("*************   TEST - OVERLAPPING BETWEEN REGULAR EMPLOYEE AND LEADER   ****************");
        Employee emp = new Employee("st8273","pass9283", "Stefan", "Batory", "stefan.batory@gmail.com");
        emp.isRegularEmployee(List.of("Java8", "Spring", "Hibernate"));
        emp.isLeader(0.05);
        System.out.println(emp);

        System.out.println();

// ASSOCIACION with ATTRIBUTE
System.out.println("*************   TEST - TEAM-EMPLOYEE associacion WITHOUT GUI   ****************");
        Team t = new Team("grupa");

        Employee e1 = new Validator("j53", "pass9374", "Jan", "Kowalski", "jan.kowalski@gmail.com", 163);
        Employee e2 = new RegularEmployee("ad05", "pass1234", "Adam", "Nowak", "adam.nowak@gmail.com");
        Employee e3 = new RegularEmployee("ad07", "pass1645", "Adrian", "Wasilewski", "adrian.wasilewski@gmail.com");
        Employee e4 = new RegularEmployee("an23", "pass8343", "Anna", "Smith", "anna.smith@gmail.com");
        Employee e5 = new RegularEmployee("p12", "pass1205", "Piotr", "Guminski", "piotr.guminski@gmail.com");
        Employee e6 = new RegularEmployee("g8362", "pass6475", "Grzegorz", "Klima", "grzegorz.klima@gmail.com");
        Employee e7 = new RegularEmployee("kr8463", "pass0947", "Krystian", "Marek", "krystian.marek@gmail.com");

        t.addEmployee(e1);
        t.addEmployee(e2);
        t.addEmployee(emp);
        t.setLeader(emp);

        System.out.println(t.toString());


        System.out.println();



// ASSOCIATION WITH ATTRIBUTE TEST
System.out.println("*************   TEST - ASSOCIACION WITH ATTRIBUTE - Task-Task_Validator-Validator   ****************");
        Project p = new ShortProject("aaa", "bbb", LocalDate.of(2020,5,15),LocalDate.of(2020,6,30),5);

        Validator v = new Validator("val123", "pass432", "Adam", "Sandler", "adam.sandler@gmail.com", 57);
        Task ts = Task.createTask(p, "gui", "implementacja okienka", Priority.low);
        Task_Validator tv = new Task_Validator(v, ts);

        System.out.println(tv.toString());

        System.out.println();



// gui launching
System.out.println("*************   TEST - LAUNCHING GUI - creating team and adding employees   ****************");

        ObjectPlus.getExtent(RegularEmployee.class).forEach(item-> {
            Gui.allEmployees.add(item);
        });
        Application.launch(Gui.class, args);

        System.out.println();



// printing Team.class extent
System.out.println("*************   TEST - PRINTING TEAM.class extent   ****************");
        System.out.println("Teams extent");
        ObjectPlus.showExtent(Team.class);

        System.out.println();

        //Persistence.save();
    }
}

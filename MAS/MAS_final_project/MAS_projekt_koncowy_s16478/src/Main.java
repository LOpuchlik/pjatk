import javafx.application.Application;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) throws Exception {

        //Persistence.load();

        //TODO Gui ma się odpalać tutaj z Maina


// checking correctness of regular inheritance between Project and ShortProject, MediumProject, LongProject classes
        ShortProject sp = new ShortProject("implementacja gui","implementacja interfejsu graficznego do obsługi.....", LocalDate.of(2020, 5, 30), LocalDate.of(2020, 6, 14), 15);
        //sp.setEndDate(LocalDate.of(2020,6 ,15));
        sp.setNumberOfFinishedTasks(13);

        MediumProject mp = new MediumProject("tworzenie bazy danych","istworzenie struktury bazy danych i zakodowanie logiki", LocalDate.of(2020, 5, 30), LocalDate.of(2020, 9, 30), 23);
        mp.setNumberOfFinishedTasks(3);

        LongProject lp = new LongProject("tworzenie logiki aplikacje","wykonanie projekowarchotektury systemu bla bla bla itd", LocalDate.of(2020, 5, 30), LocalDate.of(2021, 4, 30), 55);
        lp.setNumberOfFinishedTasks(6);
/*
        System.out.println("-----------------------------------------------------------------");
        System.out.println(sp);
        System.out.println("-----------------------------------------------------------------");
        System.out.println(mp);
        System.out.println("-----------------------------------------------------------------");
        System.out.println(lp);
        System.out.println("-----------------------------------------------------------------");*/


        System.out.println("================================================================================================");


        //Checking correct formation of composition between Project and Task classes
        Project p1 = new MediumProject("gra na iPhone'a", "napisanie w pelni funkcjonalnej gry platformowej na iPhone'a", LocalDate.of(2020,6,13), LocalDate.of(2020,12,10), 70);

        Task t1 = Task.createTask(p1,  "logowanie uzytkownika", "oprogramowanie gui do logowania użytkownika - okienko, dwa pola tekstowe, dwa przyciski, sprawdzanie poprawnosci loginu i hasla", Priority.low);

        Task t2 = Task.createTask(p1,  "rozwijany widget typu menu", "oprogramowanie gui do wyboru przez użytkownika opcji - nowej gry, zaladowania poprzednio zapisanej gry, zapisania biezacej gry lub wyjscia z gry", Priority.medium);

        p1.setNumberOfFinishedTasks(1);

        //p1.addTask(t1);
        //p1.addTask(t2);

        Project p2 = new ShortProject("to-do list", "napisanie prostego programu na androida pozwalajacego planowac rzeczy,ktore sa danego dnia do zrobienia", LocalDate.of(2020,6,02), LocalDate.of(2020,8,10), 15);

        Task t3 = Task.createTask(p2,  "okno główne", "okno główne ma pozwalać na dodawanie nowych zadań do zrobienia danego dnia i umożliwiać zaznaczanie/odznaczanie zrobionego zadania", Priority.high);

        Task t4 = Task.createTask(p2, "dodawanie tagow", "implementacja dodawania kategorii do każdego z wprowadzanych zadan w celu łatwego wyszukiwania oraz do użycia w pozniejszym etapie w module statystyk", Priority.medium);

        Task t5 = Task.createTask(p2, "moduł statystyk", "implementacja modułu statystyk, pozwalająca podejrzeć, które zadania są wykonwane powtarzalnie itp.", Priority.low);

        p2.setNumberOfFinishedTasks(1);

        //p2.addTask(t3);
        //p2.addTask(t4);
        //p2.addTask(t2);  // t2 jest juz czescia p1 - nie mozna dodac :D


/*      System.out.println("Projekt 1");
        System.out.println(p1.toString());
        System.out.println();
        System.out.println("Projekt 2");
        System.out.println(p2.toString());*/




        Team t = new Team("grupa");

        Employee e1 = new Validator("jan", "kowalski", "Jan", "Kowalski", "jan.kowalski@gmail.com");
        Employee e2 = new RegularEmployee("adam", "nowak", "Adam", "Nowak", "adam.nowak@gmail.com");

        t.addEmployee(e1);
        t.addEmployee(e2);
        t.setLeader(e2);

        System.out.println(t.toString());

        System.out.println();

// gui launching
        ObjectPlus.getExtent(RegularEmployee.class).forEach(item-> {
            Gui.allEmployees.add(item);
        });
        Application.launch(Gui.class, args);


// ASSOCIATION WITH ATTRIBUTE TEST
// ---------------------------------------------------------------------------
        Project p = new ShortProject("aaa", "bbb", LocalDate.of(2020,5,15),LocalDate.of(2020,6,30),5);

        Validator v = new Validator("val123", "pass432", "Adam", "Sandler", "adam.sandler@gmail.com");

        Task ts = Task.createTask(p, "gui", "implementacja okienka", Priority.low);

        Task_Validator tv = new Task_Validator(v, ts);

        System.out.println(tv.toString());

        //Persistence.save();
    }
}

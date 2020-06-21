import com.sun.tools.javac.util.List;
import javafx.application.Application;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) throws Exception {

       //Persistence.load();

// checking correctness of regular inheritance between Project and ShortProject, MediumProject, LongProject classes
        ShortProject sp = new ShortProject("implementacja gui","implementacja interfejsu graficznego do obsługi.....", LocalDate.of(2020, 5, 30), LocalDate.of(2020, 6, 14));

        Manager m = new Manager("man1", "Andrzej", "Zarzycki", "andrzej.zarzycki@gmail.com");

        sp.setManager(m);

        Project mp = new MediumProject("tworzenie bazy danych","istworzenie struktury bazy danych i zakodowanie logiki", LocalDate.of(2020, 5, 30), LocalDate.of(2020, 9, 30));

        Project lp = new LongProject("tworzenie logiki aplikacje","wykonanie projekowarchotektury systemu bla bla bla itd", LocalDate.of(2020, 5, 30), LocalDate.of(2021, 4, 30));


        System.out.println("-----------------------------------------------------------------");
        System.out.println(sp);
        System.out.println("-----------------------------------------------------------------");
        System.out.println(mp);
        System.out.println("-----------------------------------------------------------------");
        System.out.println(lp);
        System.out.println("-----------------------------------------------------------------");


        System.out.println();


//Checking correct formation of composition between Project and Task classes
        Project p1 = new MediumProject("gra na iPhone'a", "napisanie w pelni funkcjonalnej gry platformowej na iPhone'a", LocalDate.of(2020,6,13), LocalDate.of(2020,12,10));

        Task t1 = Task.createTask(p1,  "logowanie uzytkownika", "oprogramowanie gui do logowania użytkownika - okienko, dwa pola tekstowe, dwa przyciski, sprawdzanie poprawnosci loginu i hasla", Priority.low);

        Task t2 = Task.createTask(p1,  "rozwijany widget typu menu", "oprogramowanie gui do wyboru przez użytkownika opcji - nowej gry, zaladowania poprzednio zapisanej gry, zapisania biezacej gry lub wyjscia z gry", Priority.medium);

        p1.addTask(t1);
        p1.addTask(t2);
        p1.setNumberOfFinishedTasks(1);

        Project p2 = new ShortProject("to-do list", "napisanie prostego programu na androida pozwalajacego planowac rzeczy,ktore sa danego dnia do zrobienia", LocalDate.of(2020,6,02), LocalDate.of(2020,8,10));


        Task t3 = Task.createTask(p2,  "okno główne", "okno główne ma pozwalać na dodawanie nowych zadań do zrobienia danego dnia i umożliwiać zaznaczanie/odznaczanie zrobionego zadania", Priority.high);

        Task t4 = Task.createTask(p2, "dodawanie tagow", "implementacja dodawania kategorii do każdego z wprowadzanych zadan w celu łatwego wyszukiwania oraz do użycia w pozniejszym etapie w module statystyk", Priority.medium);

        Task t5 = Task.createTask(p2, "moduł statystyk", "implementacja modułu statystyk, pozwalająca podejrzeć, które zadania są wykonwane powtarzalnie itp.", Priority.low);

        p2.addTask(t3);
        p2.addTask(t4);
        p2.addTask(t5);
        p2.setNumberOfFinishedTasks(3);

        //p2.addTask(t2);  // t2 jest juz czescia p1 - nie mozna dodac :D


        System.out.println("Projekt 1");
        System.out.println(p1.toString());
        System.out.println();
        System.out.println("Projekt 2");
        System.out.println(p2.toString());


// OVERLAPPING BETWEEN REGULAR EMPLOYEE AND LEADER - check
        Employee emp = new Employee("st8273", "Stefan", "Batory", "stefan.batory@gmail.com");
        emp.isRegularEmployee(List.of("Java8", "Spring", "Hibernate"));
        emp.isLeader(0.05);
        //System.out.println(emp);


        Team t = new Team("grupa");

        Employee e1 = new Employee("j53",  "Robert", "Kubica", "jan.kowalski@gmail.com");
        e1.isRegularEmployee(List.of("Spring", "Java8", "Python"));
        Employee e2 = new Employee("ad05",  "Adam", "Nowak", "adam.nowak@gmail.com");
        e2.isRegularEmployee(List.of("JUnit5", "Groovy"));
        Employee e3 = new Employee("ad07",  "Adrian", "Wasilewski", "adrian.wasilewski@gmail.com");
        e3.isRegularEmployee(List.of("Scala", "C#"));
        Employee e4 = new Employee("an23",  "Anna", "Smith", "anna.smith@gmail.com");
        e4.isRegularEmployee(List.of("MatLAB", "Groovy"));
        Employee e5 = new Employee("p12",  "Piotr", "Guminski", "piotr.guminski@gmail.com");
        e5.isRegularEmployee(List.of("HTML", "JavaScript"));
        Employee e6 = new Employee("g8362",  "Grzegorz", "Klima", "grzegorz.klima@gmail.com");
        e6.isRegularEmployee(List.of("CSS", "HTML"));
        Employee e7 = new Employee("kr8463", "Krystian", "Marek", "krystian.marek@gmail.com");
        e7.isRegularEmployee(List.of("R", "Ruby"));

        t.addEmployee(e1);
        t.addEmployee(e2);
        t.addEmployee(emp);
      
        // employee 1 and 2 are already used

        System.out.println(t.toString());
        System.out.println();


// gui launching
        ObjectPlus.getExtent(Employee.class).forEach(item-> {
            Gui.allEmployees.add(item);
        });
        Application.launch(Gui.class, args);


// ASSOCIATION WITH ATTRIBUTE TEST
// ---------------------------------------------------------------------------
        Project p = new ShortProject("aaa", "bbb", LocalDate.of(2020,5,15),LocalDate.of(2020,6,30));
        p.setNumberOfFinishedTasks(1);

        Validator v = new Validator("val123",  "Adam", "Sandler", "adam.sandler@gmail.com");
        Task ts = Task.createTask(p, "gui", "implementacja okienka", Priority.low);
        Task_Validator tv = new Task_Validator(v, ts);

        System.out.println(tv.toString());

        System.out.println();

 // checking Task-Regular Employee associacion
        Task task1 = Task.createTask(p2, "task1", "some task", Priority.low);
        Employee.RegularEmployee emp1 = new Employee.RegularEmployee(List.of("Java8", "Spring"));
        Employee.RegularEmployee emp2 = new Employee.RegularEmployee(List.of("Java8", "Spring"));

        emp1.addTask(task1);
        emp2.addTask(task1);

// task and team association check
System.out.println();
//System.out.println("-------------- Task-Team association test --------------");
        Project project = new MediumProject("project", "some other project", LocalDate.of(2020,6,19), LocalDate.of(2020,11,10));

        Task taskForTeam = Task.createTask(project, "taskName", "task to be done", Priority.medium);

        Team teamforTask = new Team("team made especially for checking team-task association");

        taskForTeam.setTeamForTask(teamforTask);



//--------------

        System.out.println();

        System.out.println("+++++++++++++++++++++++++++++");
        System.out.println("Short project extent");
        ObjectPlus.showExtent(ShortProject.class);

        System.out.println("+++++++++++++++++++++++++++++");
        System.out.println("Manager extent");
        ObjectPlus.showExtent(Manager.class);

        System.out.println();

        System.out.println("+++++++++++++++++++++++++++++");
        System.out.println("Teams extent");
        ObjectPlus.showExtent(Team.class);

        System.out.println();

        System.out.println("+++++++++++++++++++++++++++++");
        System.out.println("Employee extent");
        ObjectPlus.showExtent(Employee.class);

        System.out.println();

        System.out.println("+++++++++++++++++++++++++++++");
        System.out.println("Task extent");
        ObjectPlus.showExtent(Task.class);

        //Persistence.save();
    }
}

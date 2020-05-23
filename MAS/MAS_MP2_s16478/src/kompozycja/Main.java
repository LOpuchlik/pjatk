package kompozycja;

public class Main {
    public static void main(String[] args) throws Exception {

        Project p1 = new Project("Tworzenie Gui");
        Project p2 = new Project("Tworzenie nowej sceny");


        Task t1 = Task.createTask(p1, "Stworzenie okna glownego");
        Task t2 = Task.createTask(p1, "Dodanie buttona start");
        Task t3 = Task.createTask(p1, "Dodanie text area");
        Task t4 = Task.createTask(p2, "Utworzenie glownej sceny");


        // ----------------------- pierwszy projekt --------------------
        System.out.println("-----------------------------------------------------------");
        System.out.println("\t\t\t\t\t\tProjekt 1");
        System.out.println("-----------------------------------------------------------");
        p1.addTask(t1);
        p1.addTask(t2);
        p1.addTask(t3);
        System.out.println(p1);



        // ----------------------- drugi projekt --------------------
        System.out.println("-----------------------------------------------------------");
        System.out.println("\t\t\t\t\t\tProjekt 2");
        System.out.println("-----------------------------------------------------------");
        p2.addTask(t4);
        //p2.addTask(t2); // proba dodania Taska nalezacego do innej calosci - pierwszy projekt - konczy sie bledem
        System.out.println(p2);




        // ------------------------ pusty projekt --------------------
        // proba dodania taska do nieistniejacego projektu, ktorego wartosc ustawiona jest na null
        System.out.println("-----------------------------------------------------------");
        System.out.println("\t\t\t\t\tProjekt == null");
        System.out.println("-----------------------------------------------------------");
        Project np = null;
        Task t5 = Task.createTask(np, "Puste zadania");
        np.addTask(t5);
        System.out.println(np);

    }
}

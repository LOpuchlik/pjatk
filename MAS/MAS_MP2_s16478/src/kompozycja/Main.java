package kompozycja;

public class Main {
    public static void main(String[] args) throws Exception {

        Project p = new Project("Tworzenie Gui");
        Project pr = new Project("Tworzenie nowej sceny");


        Task t1 = Task.createTask(p, "Stworzenie okna glownego");
        Task t2 = Task.createTask(p, "Dodanie buttona start");
        Task t3 = Task.createTask(pr, "Utworzenie glownej sceny");


        // ----------------------- pierwszy projekt --------------------
        System.out.println("-----------------------------------------------------------");
        System.out.println("\t\t\t\t\t\tProjekt 1");
        System.out.println("-----------------------------------------------------------");
        p.addTask(t1);
        p.addTask(t2);
        System.out.println(p);



        // ----------------------- drugi projekt --------------------
        System.out.println("-----------------------------------------------------------");
        System.out.println("\t\t\t\t\t\tProjekt 2");
        System.out.println("-----------------------------------------------------------");
        pr.addTask(t3);
        //pr.addTask(t2); // proba dodania Taska nalezacego do calosci - pierwszy projekt - konczy sie bledem
        System.out.println(pr);




        // ------------------------ pusty projekt --------------------
        // dodawanie taska do nieistniejacego projektu, ktorego wartosc ustawiona jest na null
        /*Project np = null;
        Task t4 = Task.createTask(np, "Zadania dla pustego projektu");
        np.addTask(t4);
        System.out.println("Wydruk informacji o projekcie null");
        System.out.println(np);
        */















    }
}

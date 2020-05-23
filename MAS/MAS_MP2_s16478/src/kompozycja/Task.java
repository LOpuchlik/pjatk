package kompozycja;

public class Task {

    private String taskName;
    private Project whole;

    // konstruktor jest prywatny
    private Task (Project whole, String taskName) {
        this.taskName = taskName;
        this.whole = whole;
    }

    public static Task createTask (Project whole, String taskName) throws Exception {
        // sprawdzanie czy calosc istnieje
        if (whole == null) {
            throw new Exception("Calosc nie istnieje, nie mozna stworzyc do niej czesci");
        }
        //stworzenie nowej czesci
        Task task = new Task (whole, taskName);
        // dodanie czesci do calosci
        whole.addTask(task);
        return task;
    }

/*    // do usuwania taskow dla danego projektu
    public static void removeTask (Project whole, String taskName) {

    }*/


    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}

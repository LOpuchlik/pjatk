package kompozycja;

class Task {

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
            throw new Exception("The whole (project) does not exist, therefore you cannot create a part for it");
        }
        //stworzenie nowej czesci
        Task task = new Task (whole, taskName);
        // dodanie czesci do calosci
        whole.addTask(task);
        return task;
    }

    // getter and setter
    String getTaskName() {
        return taskName;
    }
}
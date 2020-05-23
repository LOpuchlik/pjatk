package zwykla;

import java.util.ArrayList;
import java.util.List;

public class Exercise {

    private String name;
    private String difficulty;
    private String whatMusclesAreTrained;

    private List<Person> persons = new ArrayList<>();


    public Exercise(String name, String difficulty, String whatMusclesAreTrained) {
        this.name = name;
        this.difficulty = difficulty;
        this.whatMusclesAreTrained = whatMusclesAreTrained;
    }

    public void addPerson (Person newPerson) {
        if(!persons.contains(newPerson)){
            persons.add(newPerson);

            newPerson.addExercise(this);
        }
    }


    @Override
    public String toString() {
        String info = "Exercise:\n" +
                "name of exercise: " + name + "\ndifficulty: " + difficulty + "\nwhat body parts are trained: " + whatMusclesAreTrained + "\n" + "who trains:\n";

        // Add info about exercises
        for (Person p : persons) {
            info += "- " + p.getFirstName() + " " + p.getLastName() + "\n";
        }
        return info;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getWhatMusclesAreTrained() {
        return whatMusclesAreTrained;
    }

    public void setWhatMusclesAreTrained(String whatMusclesAreTrained) {
        this.whatMusclesAreTrained = whatMusclesAreTrained;
    }

}
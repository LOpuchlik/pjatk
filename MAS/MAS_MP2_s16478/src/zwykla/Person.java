package zwykla;

import java.util.ArrayList;
import java.util.List;

public class Person {

    private String firstName;
    private String lastName;
    private List<Exercise> exercises = new ArrayList<>();


    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addExercise (Exercise newExercise) {
        // checking if there's alredy info
        if (!exercises.contains(newExercise)) {
            exercises.add(newExercise);

            // and adding reverse connection
            newExercise.addPerson(this);
        }
    }

    @Override
    public String toString() {
        String info = "Person:\n" +
                firstName + " " + lastName + "\n";

        // Add info about exercises
        for (Exercise e : exercises) {
            info += "- " + e.getName() + "\n";
        }
        return info;
    }


    // getters and setters

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}

package zAtrybutem;

import java.util.ArrayList;
import java.util.List;

public class Exercise {

    private String exerciseName;
    private String difficulty;
    private List<Set_Exercise> setExercises = new ArrayList<>();

    public Exercise(String exerciseName, String difficulty) {
        this.exerciseName = exerciseName;
        this.difficulty = difficulty;
    }

    void addSetExercise(Set_Exercise newASetExercise) {
        if (!setExercises.contains(newASetExercise)) {
            setExercises.add(newASetExercise);
        }
    }

    @Override
    public String toString() {
        String info = "";
        for (Set_Exercise se: setExercises) {
            info += se.toString() + " \n";
        }
        return info;
    }

    void removeSetExercise(Set_Exercise delSetEx) {
        if (this.setExercises.contains(delSetEx)) {
            this.setExercises.remove(delSetEx);
            delSetEx.removeAssociation();
        }
    }

    String getExerciseName() {
        return exerciseName;
    }

    String getDifficulty() {
        return difficulty;
    }
}

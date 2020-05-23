package zAtrybutem;

import java.util.ArrayList;
import java.util.List;

public class Set {
    private int numberOfSetsInWorkout;
    private List<Set_Exercise> setExercises = new ArrayList<>();

    public Set(int numberOfSetsInWorkout) {
        this.numberOfSetsInWorkout = numberOfSetsInWorkout;
    }

    void addSetExercise(Set_Exercise newSetExercise) {
        if (!setExercises.contains(newSetExercise)) {
            setExercises.add(newSetExercise);
        }
    }

    @Override
    public String toString() {
        String info =  "";
        for (Set_Exercise se: setExercises) {
            info += se.toString() + " \n";
        }
        return info;
    }

    void removeSetExercise(Set_Exercise setExerciseForRemoval) {
        if (this.setExercises.contains(setExerciseForRemoval)) {
            this.setExercises.remove(setExerciseForRemoval);
            setExerciseForRemoval.removeAssociation();
        }
    }

    //getter
    int getNumberOfSetsInWorkout() {
        return numberOfSetsInWorkout;
    }
}

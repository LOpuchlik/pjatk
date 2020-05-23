package zAtrybutem;

import java.util.ArrayList;
import java.util.List;

public class Set {


    private int numberOfSetsInATraning;
    private List<Set_Exercise> setExercises = new ArrayList<>();

    public Set(int numberOfSetsInATraning) {
        this.numberOfSetsInATraning = numberOfSetsInATraning;
    }

    public void addSetExercise (Set_Exercise newSetExercise) {
        if(!setExercises.contains(newSetExercise)){
            setExercises.add(newSetExercise);

            newSetExercise.setSet(this);
        }
    }

    @Override
    public String toString() {
        String info =
                "\nname of exercise: " + setExercises.get(0);
                        //+ "numberOfSetsInATraning: " + numberOfSetsInATraning + "\nnumber of exercise repetitions: ";

        // Add info about exercises
      /*  for (Set_Exercise se : setExercises) {
            info += se.getNumberOfReps() + "\n";
        }*/
        return info;


    }

    public int getNumberOfSetsInATraning() {
        return numberOfSetsInATraning;
    }

    public void setNumberOfSetsInATraning(int numberOfSetsInATraning) {
        this.numberOfSetsInATraning = numberOfSetsInATraning;
    }
}

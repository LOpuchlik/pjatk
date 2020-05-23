package zAtrybutem;


import java.util.ArrayList;
import java.util.List;

public class Exercise {

    private String name;
    private String difficulty;
    private String whatMusclesAreTrained;

    private List<Set_Exercise> setExercises = new ArrayList<>();


    public Exercise(String name, String difficulty, String whatMusclesAreTrained) {
        this.name = name;
        this.difficulty = difficulty;
        this.whatMusclesAreTrained = whatMusclesAreTrained;
    }

    public void addSetExercise (Set_Exercise newSetExercise) {
        if(!setExercises.contains(newSetExercise)){
            setExercises.add(newSetExercise);

            newSetExercise.setExercise(this);
        }
    }


    @Override
    public String toString() {
        String info = "\nExercise:\n" + setExercises.get(0);

        info +=
             "\ndifficulty: " + difficulty + "\nwhat body parts are trained: " + whatMusclesAreTrained;

        // Add info about exercises
    /*    for (Set_Exercise se : setExercises) {
            info += se.getNumberOfReps() + "\n";
        }*/

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


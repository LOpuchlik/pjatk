package zAtrybutem;

//seria-cwiczenie * - 1 cwiczenie

public class Set_Exercise {

    private int numberOfReps;
    private Exercise exercise;
    private Set set;

    public Set_Exercise(int numberOfReps) {
        this.numberOfReps = numberOfReps;
    }

    public void setExercise (Exercise newExercise) {
        this.exercise = newExercise;
        newExercise.addSetExercise(this);
    }

    public void setSet (Set newSet) {
        this.set = newSet;
        newSet.addSetExercise(this);
    }

    @Override
    public String toString() {
        return "" + exercise.getName() + "\n" +
                "numberOfReps: " + numberOfReps + "\n"
                + "number of sets: " + set.getNumberOfSetsInATraning();
    }

    public int getNumberOfReps() {
        return numberOfReps;
    }

    public void setNumberOfReps(int numberOfReps) {
        this.numberOfReps = numberOfReps;
    }

    public Exercise getExercise() {
        return exercise;
    }
}

package zAtrybutem;


public class Set_Exercise {
    private int numberOfReps;
    private Exercise exercise;
    private Set set;

    public Set_Exercise(int numberOfReps, Exercise exercise, Set set) {
        this.numberOfReps = numberOfReps;
        this.exercise = exercise;
        this.set = set;

        // dodawanie odwrotnych asocjacji
        exercise.addSetExercise(this);
        set.addSetExercise(this);
    }


    @Override
    public String toString() {
        if (this.set == null || this.exercise == null) {
            return "Association has been removed!\n" + "Current set state: " + this.set + "\nCurrent exercise state: " + this.exercise;
        }
        return "Exercise name: \t\t\t\t\t" + this.exercise.getExerciseName()
                + "\nExercise reps: \t\t\t\t\t" + this.numberOfReps
                + "\nNumber of sets in a workout: \t" + this.set.getNumberOfSetsInWorkout()
                + "\nExercise difficulty: \t\t\t" + this.exercise.getDifficulty() +"\n";
    }

    // usuwanie polaczenia miedzy exercise a set
    public void removeAssociation() {
        if (this.exercise == null && this.set == null) {
            return;
        }
        if (this.exercise != null) {
            this.exercise.removeSetExercise(this);
            this.exercise = null;
        }
        if (this.set != null) {
            this.set.removeSetExercise(this);
            this.set = null;
        }
    }
}
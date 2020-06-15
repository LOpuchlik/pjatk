import java.time.LocalDate;


class LongProject extends Project {
    private static int minDuration; // in days

    public LongProject(String name, String description, LocalDate startDate, LocalDate desiredEndDate, int totalNumberOfTasks) {
        super(name, description, startDate, desiredEndDate, totalNumberOfTasks);
        setMinDuration(181);
    }


    public static int getMinDuration() {
        return minDuration;
    }


    /**
     *
     * @param minDuration
     * Setter is needed because the definition of minDuration can change in the future
     *  e.g. long time projects can be those that have 1-year duration and short time projects below 1 year
     */
    public static void setMinDuration(int minDuration) {
        LongProject.minDuration = minDuration;
    }


    @Override
    public String toString() {
        String msg="";

        msg +=  "Long Project\n\n"+
                "minDuration: " + getMinDuration() + "\n" +
                "name: " + name +
                "\ndescription: " + description +
                "\nstartDate: " + startDate +
                "\ndesiredEndDate: " + desiredEndDate;

        if (endDate != null)
            msg += "\nendDate: " + endDate;
        else
            msg += "\nendDate: project is not finished yet";


        msg += "\nduration: " + super.getDuration() + " day(s)" +
                "\ndelay: " + super.getDelay() +
                "\ntotalNumberOfTasks: " + totalNumberOfTasks +
                "\nnumberOfFinishedTasks: " + numberOfFinishedTasks +
                "\nprogress: " + super.getProgress();

        return msg;

    }
}

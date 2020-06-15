import java.time.LocalDate;



class ShortProject extends Project {
    private static int maxDuration; // in days

    public ShortProject(String name, String description, LocalDate startDate, LocalDate desiredEndDate, int totalNumberOfTasks) {
        super(name, description, startDate, desiredEndDate,  totalNumberOfTasks);
        setMaxDuration(90);
    }

    public static int getMaxDuration() {
        return maxDuration;
    }

    /**
     *
     * @param maxDuration
     *
     * Setter is needed because the definition of maxDuration can change in the future
     * e.g. Short time projects can be those that have 6-month duration and long time projects above 6 months
     */
    public static void setMaxDuration(int maxDuration) {
        ShortProject.maxDuration = maxDuration;
    }


    @Override
    public String toString() {
        String msg="";

        msg +=  "Short Project\n\n" +
                "maxDuration: " + getMaxDuration() + "\n" +
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



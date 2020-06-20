import java.time.LocalDate;


class LongProject extends Project {
    private static int minDuration; // in days

    public LongProject(String name, String description, LocalDate startDate, LocalDate desiredEndDate) {
        super(name, description, startDate, desiredEndDate);
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
        msg += "LONG PROJECT\n\n";
        msg += "min duration:\t\t\t\t" + getMinDuration() + " days\n";
        msg += super.toString();
        return msg;

    }
}
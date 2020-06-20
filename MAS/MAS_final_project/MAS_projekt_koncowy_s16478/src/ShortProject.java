import java.time.LocalDate;



class ShortProject extends Project {
    private static int maxDuration; // in days

    public ShortProject(String name, String description, LocalDate startDate, LocalDate desiredEndDate) {
        super(name, description, startDate, desiredEndDate);
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
        msg += "SHORT PROJECT\n\n";
        msg += "maxDuration:\t\t\t\t" + getMaxDuration() + " days\n";
        msg += super.toString();
        return msg;
    }
}

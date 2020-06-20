import java.time.LocalDate;



class ShortProject extends Project {
    private static final long serialVersionUID = 2L;
    private static int maxDuration; // in days

    public ShortProject(String name, String description, LocalDate startDate, LocalDate desiredEndDate) {
        super(name, description, startDate, desiredEndDate);
        setMaxDuration(90);
    }


    @Override
    public String toString() {
        String msg="";
        msg += "SHORT PROJECT\n\n";
        msg += "maxDuration:\t\t\t\t" + getMaxDuration() + " days\n";
        msg += super.toString();
        return msg;
    }

    // getters setters
    public static int getMaxDuration() {
        return maxDuration;
    }

    public static void setMaxDuration(int maxDuration) {
        ShortProject.maxDuration = maxDuration;
    }

}

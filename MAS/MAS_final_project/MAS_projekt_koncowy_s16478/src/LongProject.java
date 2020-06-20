import java.time.LocalDate;


class LongProject extends Project {
    private static final long serialVersionUID = 2L;
    private static int minDuration; // in days

    public LongProject(String name, String description, LocalDate startDate, LocalDate desiredEndDate) {
        super(name, description, startDate, desiredEndDate);
        setMinDuration(181);
    }

// getters setters
    public static int getMinDuration() {
        return minDuration;
    }

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
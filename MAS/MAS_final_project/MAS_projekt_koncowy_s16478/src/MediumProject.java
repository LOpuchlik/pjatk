import java.time.LocalDate;

public class MediumProject extends Project{
    private static final long serialVersionUID = 2L;

    private static int minDuration = 91; // dni
    private static int maxDuration = 180; // dni

    public MediumProject(String name, String description, LocalDate startDate, LocalDate desiredEndDate) {
        super(name, description, startDate, desiredEndDate);
        setMinDuration(91);
        setMaxDuration(180);
    }


    public int getMinDuration() {
        return minDuration;
    }

    public void setMinDuration(int minDuration) {
        this.minDuration = minDuration;
    }

    public int getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(int maxDuration) {
        this.maxDuration = maxDuration;
    }

    @Override
    public String toString() {
        String msg="";
        msg += "MEDIUM LENGTH PROJECT\n\n";
        msg += "min duration:\t\t\t\t" +  getMinDuration() + " days\n";
        msg += "max duration:\t\t\t\t" + getMaxDuration() + " days\n";
        msg += super.toString();
        return msg;
    }
}
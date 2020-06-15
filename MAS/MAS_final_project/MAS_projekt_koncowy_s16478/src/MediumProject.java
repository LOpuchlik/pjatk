import java.time.LocalDate;

public class MediumProject extends Project{

    private static int minDuration = 91; // dni
    private static int maxDuration = 180; // dni

    public MediumProject(String name, String description, LocalDate startDate, LocalDate desiredEndDate, int totalNumberOfTasks) {
        super(name, description, startDate, desiredEndDate, totalNumberOfTasks);
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

        msg +=  "Medium Project\n\n" +
                "minDuration: " + getMinDuration() +
                "\nmaxDuration: " + getMaxDuration() +
                "\nname: " + name +
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

public class Student {
   private String name;
   private double gpa;
   private String major;

    public Student(String name, double gpa, String major) {
        this.name = name;
        this.gpa = gpa;
        this.major = major;
    }
    public String getName() {
        return name;
    }
    public double getGpa() {
        return gpa;
    }
    public String getMajor() {
        return major;
    }
    public String getStudent() {
        return getName() + "\n gpa: " +  getGpa() + "\n major: " + getMajor() +"\n";
    }
}

package inheritances.dynamiczne;

public class SeniorDeveloper extends Developer {
    private int experience;


    protected SeniorDeveloper(String firstName, String lastName, int exprience) {
        super(firstName, lastName);
        this.experience = exprience;
    }

    public SeniorDeveloper(Developer dev, int experience) {
        super(dev);
        this.experience = experience;
    }


    @Override
    public String toString() {
        return ""+ getFirstName() + " " + getLastName() + " as SENIOR" +
                "\nexperience in months: " + this.experience ;
    }


    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
}
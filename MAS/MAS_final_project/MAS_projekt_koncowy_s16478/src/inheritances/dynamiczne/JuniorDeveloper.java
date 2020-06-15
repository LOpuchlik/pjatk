package inheritances.dynamiczne;

public class JuniorDeveloper extends Developer {
    int experience;


    protected JuniorDeveloper(String firstName, String lastName, int experience) {
        super(firstName, lastName);
        this.experience = experience;
    }

    @Override
    public String toString() {
        return ""+ getFirstName() + " " + getLastName() + " as JUNIOR" +
                "\nexperience in months: " + this.experience ;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
}
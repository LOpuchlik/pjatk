package inheritances.dynamiczne;

public abstract class Developer {
    private String firstName;
    private String lastName;


    protected Developer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;

    }

    protected Developer(Developer copy) {
        this.firstName = copy.firstName;
        this.lastName = copy.lastName;

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


}

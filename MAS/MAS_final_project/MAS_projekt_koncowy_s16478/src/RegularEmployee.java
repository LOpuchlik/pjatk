import java.util.List;

public class RegularEmployee extends Employee{

    // for RegularEmployee-Team association
    Team team;

    List<String> skills;

    public RegularEmployee(String login,  String firstName, String lastName, String email) {
        super(login, firstName, lastName, email);
    }

}
import java.io.Serializable;

public class Leader extends Employee implements Serializable {
    private static final long serialVersionUID = 2L;

    static double maxBonus = 0.1;
    double grantedBonus;

    public Leader(String login,  String firstName, String lastName, String email) {
        super(login,  firstName, lastName, email);
    }



}

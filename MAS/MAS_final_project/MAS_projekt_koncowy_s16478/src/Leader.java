import java.io.Serializable;

public class Leader extends Employee implements Serializable {
    private static final long serialVersionUID = 2L;

    private double maxBonus = 0.1;
    private double grantedBonus;

    public Leader(String login,  String firstName, String lastName, String email) {
        super(login,  firstName, lastName, email);
    }

// getters setters


    public double getMaxBonus() {
        return maxBonus;
    }

    public void setMaxBonus(double maxBonus) {
        this.maxBonus = maxBonus;
    }

    public double getGrantedBonus() {
        return grantedBonus;
    }

    public void setGrantedBonus(double grantedBonus) {
        this.grantedBonus = grantedBonus;
    }
}

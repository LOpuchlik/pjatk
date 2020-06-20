import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Team extends ObjectPlus {
    private static final long serialVersionUID = 2L;
    // for RegularEmployee-Team association
    private List<Employee> regEmps;

    private static Set<String> signatures = new HashSet<>();

    private String signature;
    private Employee leader;
    private int teamSize = 0;

    public Team(String signature) {
        super();
        try {
            setSignature(signature);
        } catch (Exception e) {
            e.getMessage();
        }
        this.teamSize = 0;
        regEmps = new ArrayList<>();
    }

    static Team addTeam(String signature) {
        return new Team(signature);
    }



    //******************  tu cos nie bangla  *********************

    public void setSignature(String newSignature) throws Exception {
        if (!signatures.contains(newSignature)) {
            this.signature = newSignature;
            signatures.add(newSignature);
        } else {
            throw new Exception("Signature is already used!"); // tworzy mi grupę, ale z nullem jako sygnaturą zespołu
        }
    }


    public void setLeader(Employee leader) {
        this.leader = leader;
    }

    @Override
    public String toString() {
        String msg ="";
        msg += "Team name: " + signature + "\n";
        msg += "Team size: " + teamSize + "\n";
        msg += "Team leader: " + leader + "\n";
        msg += "Team members: \n";

        if (teamSize != 0) {
            for (Employee e : regEmps) {
                msg += "      - " + e;
                msg += "\n";
            }
        } else
            msg += "EMPTY TEAM HAS BEEN CREATED, no employees has been added to the team";
        return msg;
    }

    // for RegularEmployee-Team association
    void addEmployee(Employee newRegularEmployee) {
        if(!regEmps.contains(newRegularEmployee)){
            regEmps.add(newRegularEmployee);
            this.teamSize = regEmps.size();

        // add reverse connection
            newRegularEmployee.setTeam(this);
        }
    }



    // getters setters


    public List<Employee> getRegEmps() {
        return regEmps;
    }

    public void setRegEmps(List<Employee> regEmps) {
        this.regEmps = regEmps;
    }

    public static Set<String> getSignatures() {
        return signatures;
    }

    public static void setSignatures(Set<String> signatures) {
        Team.signatures = signatures;
    }

    public String getSignature() {
        return signature;
    }

    public Employee getLeader() {
        return leader;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }
}
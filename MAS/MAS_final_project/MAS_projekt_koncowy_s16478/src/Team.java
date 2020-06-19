import java.util.ArrayList;
import java.util.List;

public class Team extends  ObjectPlus {

    static List<Employee> teamMembers = new ArrayList<>();
    static List<String> signatures = new ArrayList<>();

    String signature;
    Employee leader;
    int teamSize = 0;

    public Team(String signature) {

        try {
            setSignature(signature);
        } catch (Exception e) {
            e.getMessage();
        }
        this.teamSize = 0;
    }

    void addEmployee(Employee e) {
        teamMembers.add(e);
        this.teamSize = teamMembers.size();

        //TODO zrobic polaczenie zwrotne
        //employee.add(this);
    }

    void removeEmployee(Employee e) {
        teamMembers.remove(e);
        this.teamSize = teamMembers.size();

        //TODO usuwac polaczenie zwrotne
        //employee.add(this);
    }

    public void setSignature(String signature) throws Exception {
        if (signatures.contains(signature))
            throw new Exception("signature is already used!");
        else
            this.signature = signature;
//todo cos nie tak mi te sygnatury bierze
        signatures.add(signature);
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


        for (Employee e : teamMembers) {
            msg += "      - " + e;
            msg += "\n";
        }
        return msg;
    }
}

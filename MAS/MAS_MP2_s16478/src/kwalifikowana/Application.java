package kwalifikowana;


import java.util.ArrayList;
import java.util.List;

public class Application {

    private String uniqueSymbol;
    private String nameOfApplication;

    private List<Programmer> programmers = new ArrayList<>();


    public Application(String uniqueSymbol, String nameOfApplication) {
        this.uniqueSymbol = uniqueSymbol;
        this.nameOfApplication = nameOfApplication;
    }

    // getters and setters
    public String getUniqueSymbol() {
        return uniqueSymbol;
    }

    public void setUniqueSymbol(String uniqueSymbol) {
        this.uniqueSymbol = uniqueSymbol;
    }

    public String getNameOfApplication() {
        return nameOfApplication;
    }

    public void setNameOfApplication(String nameOfApplication) {
        this.nameOfApplication = nameOfApplication;
    }

    public List<Programmer> getProgrammers() {
        return programmers;
    }

    public void setProgrammers(List<Programmer> programmers) {
        this.programmers = programmers;
    }

    public void setProgrammer(Programmer newProgrammer) {
        programmers.add(newProgrammer);
    }


    @Override
    public String toString() {
        String info = "";
        return info + "\n     - application symbol: " + uniqueSymbol + ",  " + nameOfApplication;
    }
}

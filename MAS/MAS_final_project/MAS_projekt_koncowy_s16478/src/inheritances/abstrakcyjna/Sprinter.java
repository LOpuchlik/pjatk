package inheritances.abstrakcyjna;

public class Sprinter extends Biegacz {

    double ulubionyDystans;

    public Sprinter(String imie, String nazwisko, double ulubionyDystans) {
        super(imie, nazwisko);
        this.ulubionyDystans = ulubionyDystans;
    }

    @Override
    public String toString() {
        return "Jestem " + imie + " " + nazwisko +
                "\njestem sprinterem\n" +
                "moj ulubiony dystans to: " + ulubionyDystans + " m";
    }

}

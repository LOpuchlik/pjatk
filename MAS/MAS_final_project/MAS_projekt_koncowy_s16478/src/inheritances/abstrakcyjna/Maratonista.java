package inheritances.abstrakcyjna;

public class Maratonista extends Biegacz{

    double ulubionyDystans;

    public Maratonista(String imie, String nazwisko, double ulubionyDystans) {
        super(imie, nazwisko);
        this.ulubionyDystans = ulubionyDystans;
    }


    @Override
    public String toString() {
        return "Jestem " + imie + " " + nazwisko +
                "\njestem maratonistą\n" +
                "moj ulubiony dystans to: " + ulubionyDystans + " km";
    }
}

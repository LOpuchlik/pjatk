package inheritances.wielodziedziczenie;

// kamper jest to przyczeposamochod
public class Kamper extends Samochód implements Przyczepa {

    int liczbaMiejscDoSpania;

    public Kamper(String marka, int liczbaMiejscDoSpania) {
        super(marka);
        setLiczbeMiejscDoSpania(liczbaMiejscDoSpania); // uzyta metoda z interfejsu przyczepa

    }

    @Override
    public void setLiczbeMiejscDoSpania(int liczbaMiejscDoSpania) { // metoda z interfejsu Przyczepa
        this.liczbaMiejscDoSpania = liczbaMiejscDoSpania;
    }

    public int getLiczbaMiejscDoSpania() {
        return liczbaMiejscDoSpania;
    }

    @Override
    public String toString() {
        return "Kamper\n" +
                "marka: " + marka +
                "\nliczba miejsc do spania: " + getLiczbaMiejscDoSpania();
    }
}

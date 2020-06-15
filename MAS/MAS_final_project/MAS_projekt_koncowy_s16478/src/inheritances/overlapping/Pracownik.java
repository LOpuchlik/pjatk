package inheritances.overlapping;

// kompozycja przez klasy wewnętrzne
class Pracownik{

    String imie;
    String nazwisko;

    Developer d = null;
    Tester t = null;

    public Pracownik(String imie, String nazwisko) {
        this.imie = imie;
        this.nazwisko = nazwisko;
    }


    void isTester(String testingFramework){
        t = new Tester(testingFramework);
    }


    void isDeveloper(String language){
        d = new Developer(language);

    }

    @Override
    public String toString() {
        String info="";
                if (t == null & d == null){
                    info+="PRACOWNIK" +
                            "\nimie: " + imie +
                            "\nnazwisko: " + nazwisko;
                } else if (d == null){
                    info+="PRACOWNIK" +
                            "\nimie: " + imie +
                            "\nnazwisko: " + nazwisko;
                    info+="\n"+t;
                } else if (t == null) {
                    info+="PRACOWNIK" +
                            "\nimie: " + imie +
                            "\nnazwisko: " + nazwisko;
                    info += "\n" + d;
;                }else {
                    info+="PRACOWNIK" +
                            "\nimie: " + imie +
                            "\nnazwisko: " + nazwisko;
                    info+="\n" + d;
                    info+="\n" + t;
                }
                return info;
    }

    class Tester{
        String testingFramework;


        public Tester(String testingFramework) {
            this.testingFramework = testingFramework;
        }

        @Override
        public String toString() {
            return "AS TESTER" +
                    ", testing Framework: " + testingFramework;
        }
    }
    class Developer {
        String language;


        public Developer(String language) {
            this.language = language;
        }

        @Override
        public String toString() {
            return "AS DEVELOPER" +
                    ", language: " + language;
        }
    }
}
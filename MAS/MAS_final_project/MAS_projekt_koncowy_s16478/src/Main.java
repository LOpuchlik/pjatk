import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        Persistence.load();

        //TODO Gui ma się odpalać tutaj z Maina

        ShortProject sp = new ShortProject("implementacja gui","implementacja interfejsu graficznego do obsługi.....", LocalDate.of(2020, 5, 30), LocalDate.of(2020, 6, 14), 15);
        //sp.setEndDate(LocalDate.of(2020,6 ,15));
        sp.setNumberOfFinishedTasks(13);

        MediumProject mp = new MediumProject("tworzenie bazy danych","istworzenie struktury bazy danych i zakodowanie logiki", LocalDate.of(2020, 5, 30), LocalDate.of(2020, 9, 30), 23);
        mp.setNumberOfFinishedTasks(3);

        LongProject lp = new LongProject("tworzenie logiki aplikacje","wykonanie projekowarchotektury systemu bla bla bla itd", LocalDate.of(2020, 5, 30), LocalDate.of(2021, 4, 30), 55);
        lp.setNumberOfFinishedTasks(6);

        System.out.println("---------------------");
        System.out.println(sp);
        System.out.println("---------------------");
        System.out.println(mp);
        System.out.println("---------------------");
        System.out.println(lp);
        System.out.println("---------------------");

        Persistence.save();
    }
}

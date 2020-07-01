import java.time.LocalDate;

class SickLeave {

    private LocalDate start;
    private LocalDate finish;
    private Employee wholeEmployee;

    private SickLeave(LocalDate start, LocalDate finish, Employee wholeEmployee) {
        this.start = start;
        this.finish = finish;
        this.wholeEmployee = wholeEmployee;
    }

    static SickLeave createSickLeave (LocalDate start, LocalDate finish, Employee wholeEmployee) throws Exception {
        // checking if the whole (employee) exists
        if (wholeEmployee == null) {
            throw new Exception("The whole (employee) does not exist, therefore you cannot create a part for it");
        }
        // creating new part (sick leave)
        SickLeave sickLeave = new SickLeave (start, finish, wholeEmployee);
        // adding a part (sickleave) to a whole (employee)
        wholeEmployee.addSickLeave(sickLeave);
        return sickLeave;
    }

    LocalDate getStart() {
        return start;
    }

    void setStart(LocalDate start) {
        this.start = start;
    }

    LocalDate getFinish() {
        return finish;
    }

    void setFinish(LocalDate finish) {
        this.finish = finish;
    }

    Employee getWholeEmployee() {
        return wholeEmployee;
    }

    void setWholeEmployee(Employee wholeEmployee) {
        this.wholeEmployee = wholeEmployee;
    }
}

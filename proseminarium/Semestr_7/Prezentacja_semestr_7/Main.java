import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
        new Student("Adam", 3.60, "computer science"),
        new Student("Piotr", 3.70, "computer science"),
        new Student("Natalia", 3.50, "medicine"),
        new Student("Kamil", 3.00, "medicine"),
        new Student("Monika", 3.20, "computer science")
        );

        Stream<Student> studentsStream = students.stream();
        studentsStream
                .filter(s -> s.getMajor().equals("computer science"))
                .filter(s -> s.getGpa() > 3.25)
                .map(s -> s.getStudent().toUpperCase())
                .forEach(System.out::println);
    }
}




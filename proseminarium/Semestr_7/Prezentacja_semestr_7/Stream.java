        Stream<Student> studentsStream = students.stream();
        studentsStream
                .filter(s -> s.getMajor().equals("computer science"))
                .filter(s -> s.getGpa() > 3.25)
                .map(s -> s.getStudent().toUpperCase())
                .forEach(System.out::println);

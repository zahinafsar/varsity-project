import java.util.Scanner;

class Student {
    private int id, mark;
    private String name;

    public Student() {
        this.id = 0;
        this.mark = 0;
        this.name = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class Main {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        System.out.println("Enter the number of students");
        int n = s.nextInt();

        Student[] students = new Student[n];

        for (int i = 0; i < n; i++) {
            students[i] = new Student();

            System.out.print("\nInformation of Student " + (i + 1));
            System.out.print("\n------------------------\n");

            System.out.print("Id     : ");
            int id = s.nextInt();

            System.out.print("Name   : ");
            s.nextLine();
            String name = s.nextLine();

            System.out.print("Mark   : ");
            int mark = s.nextInt();

            students[i].setName(name);
            students[i].setId(id);
            students[i].setMark(mark);
        }

        Student firstHighestTaker = new Student();
        Student secondHighestTaker = new Student();

        for (int i = 0; i < n; i++) {
            Student student = students[i];
            if (student.getMark() > firstHighestTaker.getMark()) {
                secondHighestTaker = firstHighestTaker;
                firstHighestTaker = student;
            } else if (student.getMark() > secondHighestTaker.getMark()) {
                secondHighestTaker = student;
            }
        }

        System.out.print("\nSecond Hightest Mark");
        System.out.print("\n----------------------");
        System.out.print("\nId     : " + secondHighestTaker.getId());
        System.out.print("\nName   : " + secondHighestTaker.getName());
        System.out.print("\nMark   : " + secondHighestTaker.getMark());
    }
}
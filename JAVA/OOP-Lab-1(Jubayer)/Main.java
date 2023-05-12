import java.util.Scanner;

class Teacher {
    private String _name;
    private int _age;
    private String _dept;
    private String _sub;

    // Constructor
    public Teacher() {
        _name = "";
        _age = 0;
        _dept = "";
        _sub = "";
    }

    public Teacher(String name, int age, String dept, String sub) {
        _name = name;
        _age = age;
        _dept = dept;
        _sub = sub;
    }

    // Setter methods for private properties
    public void setName(String name) {
        _name = name;
    }

    public void setAge(int age) {
        _age = age;
    }

    public void setDept(String dept) {
        _dept = dept;
    }

    public void setSub(String sub) {
        _sub = sub;
    }

    // Getter methods for private properties
    public String getName() {
        return _name;
    }

    public int getAge() {
        return _age;
    }

    public String getDept() {
        return _dept;
    }

    public String getSub() {
        return _sub;
    }

    // Print the properties of the teacher
    public void printProperties() {
        System.out.println("Name: " + _name);
        System.out.println("Age: " + _age);
        System.out.println("Department: " + _dept);
        System.out.println("Subject: " + _sub);
    }
}

// execution of the program

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get the array length from the user
        System.out.print("Enter the number of teachers: ");
        int length = scanner.nextInt();
        Teacher[] teachers = new Teacher[length];

        // Get the object properties from the user
        for (int i = 0; i < length; i++) {
            Teacher teacher = new Teacher();

            System.out.println("Enter the properties for teacher " + (i + 1) + ":");

            System.out.print("Name: ");
            String name = scanner.next();
            teacher.setName(name);

            System.out.print("Age: ");
            int age = scanner.nextInt();
            teacher.setAge(age);

            System.out.print("Department: ");
            String dept = scanner.next();
            teacher.setDept(dept);

            System.out.print("Subject: ");
            String sub = scanner.next();
            teacher.setSub(sub);

            teachers[i] = teacher;
        }

        scanner.close();

        // Find the second most older teacher based on their _age property
        int oldestIndex = 0;
        int secondOldestIndex = 0;
        for (int i = 0; i < teachers.length; i++) {
            if (teachers[i].getAge() > teachers[oldestIndex].getAge()) {
                secondOldestIndex = oldestIndex;
                oldestIndex = i;
            } else if (teachers[i].getAge() > teachers[secondOldestIndex].getAge() && i != oldestIndex) {
                secondOldestIndex = i;
            }
        }

        // Print the properties of the second most older teacher
        teachers[secondOldestIndex].printProperties();
    }
}
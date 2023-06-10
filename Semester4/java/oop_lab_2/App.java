import java.util.Scanner;
import java.lang.Math;

public class App {
    private static final int MAX_LENGTH = 11;

    public double calculateSquareRoot(String str) {
        try {
            checkStringLength(str);
            return Math.sqrt(str.length());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return 0.0;
    }

    public int calculateSquare(String str) {
        try {
            checkStringLength(str);
            return str.length() * str.length();
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return 0;
    }

    public int calculateModulus(String str) {
        try {
            checkStringLength(str);
            return str.length() % 2;
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return 0;
    }

    private void checkStringLength(String str) {
        if (str.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("String length exceeds the maximum allowed length of " + MAX_LENGTH + ".");
        }
    }

    public static void main(String[] args) {
        App calculator = new App();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the value of s1: ");
        String s1 = scanner.nextLine();

        System.out.print("Enter the value of newId: ");
        String newId = scanner.nextLine();

        scanner.close();

        String s2 = s1 + newId;

        String s3 = "";
        try {
            s3 = s1.replace(s2, "");
        } catch (Exception e) {
            System.out.println("Error during subtraction: " + e.getMessage());
        }

        int s3Length = calculator.calculateSquare(s3);

        System.out.println("Square root of s3 length: " + calculator.calculateSquareRoot(String.valueOf(s3Length)));
        System.out.println("Square of s3 length: " + calculator.calculateSquare(String.valueOf(s3Length)));
        System.out.println("Modulus of s3 length: " + calculator.calculateModulus(String.valueOf(s3Length)));
    }
}

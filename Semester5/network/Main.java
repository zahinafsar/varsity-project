package network;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Do you want to convert:");
        System.out.println("1. Decimal dotted to Binary dotted (d to b)");
        System.out.println("2. Binary dotted to Decimal dotted (b to d)");
        System.out.println("3. Hexadecimal to Binary IPv6 (h to b)");
        System.out.println("4. Binary to Hexadecimal IPv6 (b to h)");
        System.out.print("Enter your choice (1, 2, 3, or 4): ");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        IPAddressConverter converter = null;

        switch (choice) {
            case 1:
                converter = new DecimalToBinaryConverter();
                System.out.print("Enter the decimal dotted IP address: ");
                break;

            case 2:
                converter = new BinaryToDecimalConverter();
                System.out.print("Enter the binary dotted IP address: ");
                break;

            case 3:
                converter = new HexadecimalToBinaryIPv6Converter();
                System.out.print("Enter the hexadecimal IPv6 address: ");
                break;

            case 4:
                converter = new BinaryToHexadecimalIPv6Converter();
                System.out.print("Enter the binary IPv6 address: ");
                break;

            default:
                System.out.println("Invalid choice. Please enter 1, 2, 3, or 4.");
                System.exit(0);
        }

        String input = scanner.nextLine();
        String result = converter.convert(input);
        System.out.println("Converted IP address: " + result);

        scanner.close();
    }
}

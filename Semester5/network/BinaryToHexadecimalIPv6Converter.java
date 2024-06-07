package network;

public class BinaryToHexadecimalIPv6Converter implements IPAddressConverter {

    @Override
    public String convert(String binaryIP) {
        String[] groups = binaryIP.split(":");
        StringBuilder hexIP = new StringBuilder();

        for (String group : groups) {
            int decimalValue = Integer.parseInt(group, 2);
            String hexValue = Integer.toHexString(decimalValue).toUpperCase();
            hexIP.append(hexValue).append(':');
        }

        // Remove the last colon
        hexIP.setLength(hexIP.length() - 1);
        return hexIP.toString();
    }
}

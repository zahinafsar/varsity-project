package network;

public class HexadecimalToBinaryIPv6Converter implements IPAddressConverter {

    @Override
    public String convert(String hexIP) {
        String[] groups = hexIP.split(":");
        StringBuilder binaryIP = new StringBuilder();

        for (String group : groups) {
            int decimalValue = Integer.parseInt(group, 16);
            String binaryValue = String.format("%16s", Integer.toBinaryString(decimalValue)).replace(' ', '0');
            binaryIP.append(binaryValue).append(':');
        }

        // Remove the last colon
        binaryIP.setLength(binaryIP.length() - 1);
        return binaryIP.toString();
    }
}

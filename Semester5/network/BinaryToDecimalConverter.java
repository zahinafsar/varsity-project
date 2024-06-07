package network;

public class BinaryToDecimalConverter implements IPAddressConverter {

    @Override
    public String convert(String binaryIP) {
        String[] octets = binaryIP.split("\\.");
        StringBuilder decimalIP = new StringBuilder();

        for (String octet : octets) {
            int decimalValue = Integer.parseInt(octet, 2);
            decimalIP.append(decimalValue).append('.');
        }

        // Remove the last dot
        decimalIP.setLength(decimalIP.length() - 1);
        return decimalIP.toString();
    }
}

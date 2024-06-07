package network;

public class DecimalToBinaryConverter implements IPAddressConverter {

    @Override
    public String convert(String decimalIP) {
        String[] octets = decimalIP.split("\\.");
        StringBuilder binaryIP = new StringBuilder();

        for (String octet : octets) {
            int decimalValue = Integer.parseInt(octet);
            String binaryValue = String.format("%8s", Integer.toBinaryString(decimalValue)).replace(' ', '0');
            binaryIP.append(binaryValue).append('.');
        }

        // Remove the last dot
        binaryIP.setLength(binaryIP.length() - 1);
        return binaryIP.toString();
    }
}

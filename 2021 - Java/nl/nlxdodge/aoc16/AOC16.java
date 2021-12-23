package nl.nlxdodge.aoc16;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AOC16 {
    private static final String FOLDER_NAME = MethodHandles.lookup().lookupClass().getSimpleName().toLowerCase();
    private static final String FILE_PATH = String.format("./nl/nlxdodge/%s/input.txt", FOLDER_NAME);

    public static void main(String[] args) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            String transmission = stream.toList().get(0);

            String transmissionBinary = hexToBin(transmission);

            
            Integer version = Integer.parseInt(transmissionBinary.substring(0, 3), 2);
            Integer versionTotal = 0;
            
            Integer typeId = Integer.parseInt(transmissionBinary.substring(3, 6), 2);
            versionTotal = parseTransmission(version, typeId, transmissionBinary);

            Integer result1 = versionTotal;
            System.out.println(String.format("Result 1: %s", result1));

            // code for part two

            String result2 = "";
            System.out.println(String.format("Result 2: %s", result2));
        }
    }

    private static Integer parseTransmission(Integer version, Integer typeId, String transmission) {
        Integer versionTotal = version;
        if (typeId == 4) {
            // System.out.println(parsePackets(transmission.substring(6, transmission.length()), typeId));
        } else {
            // is operator
            Integer lengthTypeId = Integer.parseInt(transmission.substring(6, 7));
            Integer lengthTypeIdLength = lengthTypeId == 0 ? 15 : 11;
            Integer length = Integer.parseInt(transmission.substring(7, 7 + lengthTypeIdLength), 2);
            Integer beginning = lengthTypeId == 0 ? 22 : 18;


            String newPacket = transmission.substring(beginning, transmission.length());
            version = Integer.parseInt(newPacket.substring(0, 3), 2);
            System.out.println(String.format("Total %s", versionTotal));
            versionTotal += version;
            System.out.println(String.format("Add %s  Total %s", version, versionTotal));
            typeId = Integer.parseInt(newPacket.substring(3, 6), 2);
            versionTotal = parseTransmission(versionTotal, typeId, newPacket);


            // List<Integer> packets = parsePackets(transmission.substring(beginning, transmission.length()), typeId,
            //         lengthTypeId, length);

            // System.out.println(packets);
        }
        return versionTotal;
    }

    public static List<Integer> parsePackets(String bitsInTransmission, Integer typeId) {
        return parsePackets(bitsInTransmission, typeId, -1, -1);
    }

    public static List<Integer> parsePackets(String bitsInTransmission, Integer typeId, Integer lengthTypeId,
            Integer lengthTypeIdLength) {
        List<Integer> packets = new ArrayList<>();
        StringBuilder actualBits = new StringBuilder();

        if (typeId != 0 && typeId != 1) {
            lengthTypeId = Integer.parseInt(bitsInTransmission.substring(6, 7));
            lengthTypeIdLength = lengthTypeId == 0 ? 15 : 11;
            Integer length = Integer.parseInt(bitsInTransmission.substring(7, 7 + lengthTypeIdLength), 2);
            Integer beginning = lengthTypeId == 0 ? 22 : 18;
            return parsePackets(bitsInTransmission.substring(beginning, bitsInTransmission.length()), typeId, lengthTypeId,
                    length);
        }

        // transmission = transmission.replaceAll("0+$", "");

        Integer bitLength = lengthTypeId == -1 ? 5 : 11;

        if (lengthTypeId == 0) {
            bitsInTransmission = bitsInTransmission.substring(0, lengthTypeIdLength);
        }
        while (!bitsInTransmission.isEmpty()) {
            String bit = "";
            bit = bitsInTransmission.substring(0,
                    bitsInTransmission.length() >= bitLength ? bitLength : bitsInTransmission.length());
            bitsInTransmission = bitsInTransmission.replace(bit, "");
            actualBits.append(bit.substring(bit.length() - 4, bit.length()));
            System.out.println(actualBits);

            if (bit.substring(bit.length() - 5, bit.length() - 4).equals("0")) {
                packets.add(Integer.parseInt(actualBits.toString(), 2));
                System.out.println("Packets: " + packets);
                actualBits = new StringBuilder();

                if (lengthTypeId == -1 && lengthTypeIdLength == -1) {
                    return packets;
                }

                if (lengthTypeId == 1 && packets.size() == lengthTypeIdLength) {
                    return packets;
                }
            }
        }
        return packets;
    }

    public static String hexToBin(String hex) {
        hex = hex.replaceAll("0", "0000");
        hex = hex.replaceAll("1", "0001");
        hex = hex.replaceAll("2", "0010");
        hex = hex.replaceAll("3", "0011");
        hex = hex.replaceAll("4", "0100");
        hex = hex.replaceAll("5", "0101");
        hex = hex.replaceAll("6", "0110");
        hex = hex.replaceAll("7", "0111");
        hex = hex.replaceAll("8", "1000");
        hex = hex.replaceAll("9", "1001");
        hex = hex.replaceAll("A", "1010");
        hex = hex.replaceAll("B", "1011");
        hex = hex.replaceAll("C", "1100");
        hex = hex.replaceAll("D", "1101");
        hex = hex.replaceAll("E", "1110");
        hex = hex.replaceAll("F", "1111");
        return hex;
    }
}

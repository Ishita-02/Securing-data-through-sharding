package com.example.hashPackage;

import java.io.*;
import java.nio.charset.StandardCharsets;
public class Hash {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java HashFunction <filePath>");
            System.exit(1);
        }

        String filePath = args[0];
        try {
            String hash = customHashFromFile(filePath);
            System.out.println("Custom Hash for file '" + filePath + "': " + hash);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public static String customHash(String data) {
        int hash = 0;

        for (int i = 0; i < data.length(); i++) {
            hash = (hash * 31 + data.charAt(i)) % 1000000007;
        }

        return Integer.toHexString(hash);
    }

    public static String customHashFromFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                content.append(line);
            }

            return customHash(content.toString());
        }
    }

    public static int hash(String string) {
        return 0;
    }

    public String readLedgerDataFromFile(String string) {
        return null;
    }
}

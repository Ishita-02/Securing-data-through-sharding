package com.example.lookupPackage;
import java.io.*;
import java.util.StringTokenizer;

public class LookupTable {

    public static void createLookupTable(String[] outputFolders) {
        try (PrintWriter writer = new PrintWriter("lookupTable.txt")) {
            for (int i = 0; i < outputFolders.length; i++) {
                if (i > 0) {
                    writer.print("|");
                }
                writer.print("User" + (i + 1));
                for (int j = 1; j <= outputFolders.length; j++) {
                    writer.print("," + outputFolders[j - 1] + "/User" + (i + 1) + "part" + j + ".txt");
                }
            }
            System.out.println("Lookup table created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printUserEntries(int userId) {
        try (BufferedReader reader = new BufferedReader(new FileReader("lookupTable.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, "|");
                while (tokenizer.hasMoreTokens()) {
                    String userEntry = tokenizer.nextToken();
                    StringTokenizer userTokenizer = new StringTokenizer(userEntry, ",");
                    String currentUserId = userTokenizer.nextToken();
                    if (currentUserId.equals("User" + userId)) { 
                        System.out.printf("%-30s%n", "File Part");
                        System.out.println("------------------------------");
                        System.out.println("User ID: " + userId);
                        while (userTokenizer.hasMoreTokens()) {
                            System.out.println("File Part: " + userTokenizer.nextToken());
                        }
                        break; 
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    // public static void main(String[] args) {
    //     String[] outputFolders = {
    //             "DestinationFolder1",
    //             "DestinationFolder2",
    //             "DestinationFolder3",
    //             "DestinationFolder4",
    //             "DestinationFolder5"
    //     };

    //     createLookupTable(outputFolders);
    // }
}

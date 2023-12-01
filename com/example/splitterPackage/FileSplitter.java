package com.example.splitterPackage;
import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Scanner;

public class FileSplitter {

    public static void splitAndStoreUserDetails(int userId, String[] outputFolders) {
        for (String folderPath : outputFolders) {
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
        }

        String inputFile = "encryptedFiles" + File.separator + "encryptedFile" + "User" + userId + ".txt";
        int totalParts = outputFolders.length;

        try (FileInputStream fileInputStream = new FileInputStream(inputFile)) {
            FileChannel fileChannel = fileInputStream.getChannel();
            long fileSize = fileChannel.size();
            long partSize = fileSize / totalParts;

            // Store each part in a separate folder (part 1 to part totalParts)
            for (int i = 0; i < totalParts; i++) {
                String outputFilePath = outputFolders[i] + File.separator + "User" + userId + "part" + (i + 1) + ".txt";
                try (FileOutputStream fileOutputStream = new FileOutputStream(outputFilePath);
                     FileChannel outputChannel = fileOutputStream.getChannel()) {

                    long position = i * partSize;
                    long remainingBytes = (i == totalParts - 1) ? (fileSize - position) : partSize;

                    fileChannel.transferTo(position, remainingBytes, outputChannel);
                }
            }

            System.out.println("User details divided into parts and stored in different folders successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // public static void main(String[] args) {
    //     Scanner scanner = new Scanner(System.in);

    //     // Take user input for the user ID
    //     System.out.print("Enter User ID: ");
    //     int userId = scanner.nextInt();

    //     // Specify the output folders
    //     String[] outputFolders = {
    //             "DestinationFolder1",
    //             "DestinationFolder2",
    //             "DestinationFolder3",
    //             "DestinationFolder4",
    //             "DestinationFolder5"
    //     };

    //     // Call the method to split and store user details
    //     splitAndStoreUserDetails(userId, outputFolders);

    //     // Close the scanner
    //     scanner.close();
    // }
}

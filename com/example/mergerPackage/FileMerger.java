package com.example.mergerPackage;
import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Scanner;

public class FileMerger {

    public static void mergeUserDetails(int userId, String[] inputFolders, String outputFileName) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputFileName);
             FileChannel outputChannel = fileOutputStream.getChannel()) {

            for (int i = 0; i < inputFolders.length; i++) {
                String inputFilePath = inputFolders[i] + File.separator + "User" + userId + "part" + (i + 1) + ".txt";
                try (FileInputStream fileInputStream = new FileInputStream(inputFilePath);
                     FileChannel inputChannel = fileInputStream.getChannel()) {

                    inputChannel.transferTo(0, inputChannel.size(), outputChannel);
                }
            }

            System.out.println("User details merged successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // public static void main(String[] args) {
    //     Scanner scanner = new Scanner(System.in);

    //     // Take user input for the user ID
    //     System.out.print("Enter User ID: ");
    //     int userId = scanner.nextInt();

    //     // Specify the input folders
    //     String[] inputFolders = {
    //             "DestinationFolder1",
    //             "DestinationFolder2",
    //             "DestinationFolder3",
    //             "DestinationFolder4",
    //             "DestinationFolder5"
    //     };

    //     // Specify the output file name
    //     String outputFileName = "mergedFile" + "User" +  userId + ".txt";

    //     // Call the method to merge user details
    //     mergeUserDetails(userId, inputFolders, outputFileName);

    //     // Close the scanner
    //     scanner.close();
    // }
}

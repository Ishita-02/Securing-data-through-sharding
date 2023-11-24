package Splitter;
import java.io.*;
import java.nio.channels.FileChannel;
import java.util.HashMap;

public class FileSplitter {
    public static void splitAndStoreFile(String inputFile, String[] outputFolders, String lookupTableFile) {
        for (String folderPath : outputFolders) {
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
        }

        HashMap<Integer, String> lookupTable = readLookupTableFromFile(lookupTableFile);
        int userId = lookupTable.size() + 1;
        
        try (FileInputStream fileInputStream = new FileInputStream(inputFile)) {
            FileChannel fileChannel = fileInputStream.getChannel();
            long fileSize = fileChannel.size();
            long partSize = fileSize / outputFolders.length;

            for (int i = 0; i < outputFolders.length; i++) {
                String outputFilePath = outputFolders[i] + File.separator + "part" + (i + 1) + ".txt";
                try (FileOutputStream fileOutputStream = new FileOutputStream(outputFilePath);
                     FileChannel outputChannel = fileOutputStream.getChannel()) {

                    long position = i * partSize;
                    long remainingBytes = (i == outputFolders.length - 1) ? (fileSize - position) : partSize;

                    // Transfer data from input to output
                    fileChannel.transferTo(position, remainingBytes, outputChannel);
                    lookupTable.put(userId, outputFilePath);
                    //userId++; // Increment the user ID for the next file part
                }
            }

            // Print the lookup table for reference
            for (int id : lookupTable.keySet()) {
                System.out.println("User ID: " + id + " | File Part: " + lookupTable.get(id));
            }

            // Save the updated lookup table to a file
            saveLookupTableToFile(lookupTableFile, lookupTable);

            System.out.println("File divided into parts and stored in different folders successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static HashMap<Integer, String> readLookupTableFromFile(String fileName) {
        HashMap<Integer, String> lookupTable = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    int userId = Integer.parseInt(parts[0]);
                    String filePart = parts[1];
                    lookupTable.put(userId, filePart);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lookupTable;
    }

    public static void saveLookupTableToFile(String fileName, HashMap<Integer, String> lookupTable) {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (int userId : lookupTable.keySet()) {
                String filePart = lookupTable.get(userId);
                writer.write(userId + "," + filePart + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

    class MainFileSplitter {
        public static void main(String[] args) {
            String inputFile = "sampleData.txt";
            String[] outputFolders = {
                "DestinationFolder1",
                "DestinationFolder2",
                "DestinationFolder3",
                "DestinationFolder4",
                "DestinationFolder5"
            };
            String lookupTableFile = "minor\\lookupTable.txt";
            FileSplitter.splitAndStoreFile(inputFile, outputFolders, lookupTableFile);
        }
    }
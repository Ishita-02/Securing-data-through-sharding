import java.io.*;
import java.nio.channels.FileChannel;

public class FileSplitter {
    public static void main(String[] args) {
        // Specify the input file path
        String inputFile = "sampleData.txt";

        // Specify the output folder paths
        String[] outputFolders = {
            "DestinationFolder1",
            "DestinationFolder2",
            "DestinationFolder3",
            "DestinationFolder4",
            "DestinationFolder5"
        };

        // Ensure the output folders exist, create them if necessary
        for (String folderPath : outputFolders) {
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
        }

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
                }
            }

            System.out.println("File divided into parts and stored in different folders successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

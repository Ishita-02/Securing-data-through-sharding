import java.util.*;
import java.nio.channels.*;
import java.io.*;

public class FileMerger {
    public static void main(String[] args) {
        // Specify the output file path (where the merged content will be saved)
        String outputFile = "Merged file.txt";

        // Specify the paths of the divided parts
        String[] partPaths = {
            "DestinationFolder1\\part1.txt",
            "DestinationFolder2\\part2.txt",
            "DestinationFolder3\\part3.txt",
            "DestinationFolder4\\part4.txt",
            "DestinationFolder5\\part5.txt"
        };

        try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
             FileChannel outputChannel = fileOutputStream.getChannel()) {

            List<FileChannel> inputChannels = new ArrayList<>();

            // Open each part file and create a FileChannel for it
            for (String partPath : partPaths) {
                FileInputStream fileInputStream = new FileInputStream(partPath);
                FileChannel inputChannel = fileInputStream.getChannel();
                inputChannels.add(inputChannel);
            }

            // Iterate through the input channels and transfer data to the output channel
            for (FileChannel inputChannel : inputChannels) {
                inputChannel.transferTo(0, inputChannel.size(), outputChannel);
                inputChannel.close();
            }

            System.out.println("File parts merged successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

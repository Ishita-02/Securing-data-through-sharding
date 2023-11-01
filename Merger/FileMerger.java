package Merger;
import java.io.*;
import java.nio.channels.*;
import java.util.List;
import java.util.ArrayList;

public class FileMerger {

    public static void mergeFiles(String outputFile, String[] partPaths) {
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
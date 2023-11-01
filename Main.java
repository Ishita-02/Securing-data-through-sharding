import java.util.*;
import Splitter.*;
import Cipher.*;
import Merger.*;

    class Main {
    public static void main(String[] args) {

        String inputFilePath = "personal_details.txt";
        String encryptFilePath = "encryptedFile.txt";
        String decryptFilePath = "decryptedFile.txt";
        String lookupTableFile = "lookupTable.txt";
        String[] outputFolders = {
        "DestinationFolder1",
        "DestinationFolder2",
        "DestinationFolder3",
        "DestinationFolder4",
        "DestinationFolder5"};
         String outputFile = "MergedFile.txt";
        String[] partPaths = {
        "DestinationFolder1\\part1.txt",
        "DestinationFolder2\\part2.txt",
        "DestinationFolder3\\part3.txt",
        "DestinationFolder4\\part4.txt",
        "DestinationFolder5\\part5.txt"};
     boolean exit=false;
     while(!exit){
        Scanner in=new Scanner(System.in);
        System.out.println("press 1 to enter details ");
        System.out.println("press 2 to view data  ");
        System.out.println("press 3 to view ledger  ");
        System.out.println("press 4 to logout   ");
        int choice = in.nextInt();
        
        switch (choice) {
            case 1:
                    PersonalDetailsToFile.main(args);
                    System.out.println();
                    break;
            case 2:
                
                int key = 3; 
                CaesarCipher obj1 = new CaesarCipher();
                
                obj1.encryptFile(inputFilePath, encryptFilePath, key);
                System.out.println();
                        
                FileSplitter.splitAndStoreFile(encryptFilePath, outputFolders, lookupTableFile);
                FileMerger.mergeFiles(outputFile, partPaths);
                obj1.decryptFile(encryptFilePath, decryptFilePath, key);
                System.out.println("File has been decrypted and stored in txt format");
                break;
            case 3:
                System.out.println("View ledger");
                break;
            case 4: 
                System.out.println("Logout");
                exit=true;
                break;
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                break;
        }
        }
    }
}
    
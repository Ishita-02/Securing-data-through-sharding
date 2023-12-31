import com.example.ledgerPackage.Ledger;
import com.example.lookupPackage.LookupTable;
import com.example.splitterPackage.FileSplitter;
import com.example.UserManagerPackage.UserManager;
import Cipher.PersonalDetailsToFile;
import com.example.mergerPackage.FileMerger;
import com.example.cipherPackage.CaesarCipher;
import com.example.hashPackage.Hash;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.crypto.Cipher;

class Main {
    public static void main(String[] args) {
        int userId = 0;

        String inputFilePath = "personalDetailsUser" + userId + ".txt";
        String[] outputFolders = {
                "DestinationFolder1",
                "DestinationFolder2",
                "DestinationFolder3",
                "DestinationFolder4",
                "DestinationFolder5"};
        String outputFile = "mergedFileUser" + userId + ".txt";
        
        boolean exit = false;
        
        UserManager userManager = new UserManager();
        Scanner scanner = new Scanner(System.in);

        try {
            userManager.loadData();

            System.out.println("Enter 'register' to register a new user or 'login' to log in:");
            String choice = scanner.nextLine();

            if (choice.equals("register")) {
                System.out.println("Enter username:");
                String username = scanner.nextLine();

                System.out.println("Enter password:");
                String password = scanner.nextLine();

                userManager.registerUser(username, password);
                userManager.loginUser(username, password);
            } else if (choice.equals("login")) {
                System.out.println("Enter username:");
                String username = scanner.nextLine();

                System.out.println("Enter password:");
                String password = scanner.nextLine();

                userManager.loginUser(username, password);
            } else {
                System.out.println("Invalid choice. Please enter 'register' or 'login'.");
            }
        } catch (IOException e) {
            System.out.println("Error accessing user data file.");
            e.printStackTrace();
        }

        while (!exit) {
            Scanner in = new Scanner(System.in);
            System.out.println("press 1 to enter details ");
            System.out.println("press 2 to view data  ");
            System.out.println("press 3 to view ledger  ");
            System.out.println("press 4 to logout   ");
            int choice = in.nextInt();

            switch (choice) {
                case 1:
                    PersonalDetailsToFile details = new PersonalDetailsToFile();
                    details.main(args);
                    System.out.println();
                    break;
                case 2:
                    int key = 3;
                    Scanner sc = new Scanner(System.in);
                    System.out.println("Enter userId");
                    userId = sc.nextInt();
                    inputFilePath = "personalDetailsUser" + userId + ".txt"; 
                    outputFile = "mergedFileUser" + userId + ".txt";
                    String[] partPaths = {
                    "DestinationFolder1" + File.separator + "User"+ userId + "part1.txt",
                    "DestinationFolder2" + File.separator + "User" + userId +  "part2.txt",
                    "DestinationFolder3" + File.separator + "User" + userId +  "part3.txt",
                    "DestinationFolder4" + File.separator + "User" + userId +  "part4.txt",
                    "DestinationFolder5" + File.separator + "User" + userId +  "part5.txt"};
                    CaesarCipher obj1 = new CaesarCipher();
                    obj1.encryptFile(inputFilePath, userId, key);
                    FileSplitter.splitAndStoreUserDetails(userId, outputFolders);
                    FileMerger.mergeUserDetails(userId, outputFolders, outputFile);
                    obj1.decryptFile(userId, key);
                    System.out.println("Lookup table");
                    LookupTable.createLookupTable(outputFolders);
                    LookupTable.printUserEntries(userId);
                    break;
                case 3:
                    System.out.println("View ledger");
                    Ledger ledger = new Ledger();
                    String ledgerData = ledger.readLedgerDataFromFile("ledger.txt");
                    // Get user input for new block data
                    int transactionId = ledger.generateRandomBlockchainId();
                    System.out.print("Enter user ID: ");
                    int userid = scanner.nextInt();
                    scanner.nextLine(); 

                    // Calculate the previous hash as the current hash of the latest block
                    String prevHash = ledger.calculatePreviousHash(ledgerData);

                    // Calculate the current hash using HashGenerator
                    String currentFilePath = "personalDetailsUser" + userid + ".txt";
                    String currHash = Hash.customHash(currentFilePath);

                    // Print the calculated current hash
                    System.out.println("Current hash is: " + currHash);
                    String timestamp = ledger.getCurrentTimestamp();

                    // Generate a random blockchain ID
                    int blockchainId = 1000;


                    // Create a new block entry
                    String newBlock = blockchainId + "," + transactionId + "," + userid + "," + prevHash + "," + currHash + "," + timestamp;

                    // Append the new block to the ledger data
                    if (!ledgerData.isEmpty()) {
                        ledgerData += "|";
                    }
                    ledgerData += newBlock;

                    // Write the updated ledger data to a file
                    ledger.writeLedgerDataToFile("ledger.txt", ledgerData);

                    System.out.println("New block added to the ledger.");

                    Ledger genesisBlock = new Ledger();
                    ledger.displayBlockDetails(genesisBlock);

                    // Display the updated ledger
                    ledger.displayLedger(ledgerData);
                    break;
                case 4:
                    System.out.println("Logout");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                    break;
            }
        }
    }
}

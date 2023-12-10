package com.example.ledgerPackage;
import com.example.hashPackage.*;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Scanner;
public class Ledger {
    private int blockchainId;
    private int transactionId;
    private int userId;
    private String prevHash;
    private String currHash;
    private String timestamp;

    public Ledger() {
        this.blockchainId = 1000;
        this.transactionId = 0;
        this.userId = 0;
        this.prevHash = "0";
        this.currHash = "2ef7bde608";
        this.timestamp = getCurrentTimestamp();
    }

    public Ledger(int blockchainId, int transactionId, int userId, String prevHash, String currHash, String timestamp) {
        this.blockchainId = blockchainId;
        this.transactionId = transactionId;
        this.userId = userId;
        this.prevHash = prevHash;
        this.currHash = currHash;
        this.timestamp = timestamp;
    }
    public static void main(String[] args) {

        String ledgerData = readLedgerDataFromFile("ledger.txt");
        // Get user input for new block data
        Scanner scanner = new Scanner(System.in);
        // System.out.print("Enter transaction ID: ");
        // int transactionId = scanner.nextInt();
        int transactionId = generateRandomBlockchainId();
        System.out.print("Enter user ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); 

        // Calculate the previous hash as the current hash of the latest block
        String prevHash = calculatePreviousHash(ledgerData);

        // Calculate the current hash using HashGenerator
        String currentFilePath = "personalDetailsUser" + userId + ".txt";
        String currHash = Hash.customHash(currentFilePath);

        // Print the calculated current hash
        System.out.println("Current hash is: " + currHash);
        String timestamp = getCurrentTimestamp();

        // Generate a random blockchain ID
        int blockchainId = 1000;


        // Create a new block entry
        String newBlock = blockchainId + "," + transactionId + "," + userId + "," + prevHash + "," + currHash + "," + timestamp;

        // Append the new block to the ledger data
        if (!ledgerData.isEmpty()) {
            ledgerData += "|";
        }
        ledgerData += newBlock;

        // Write the updated ledger data to a file
        writeLedgerDataToFile("ledger.txt", ledgerData);

        System.out.println("New block added to the ledger.");

        Ledger genesisBlock = new Ledger();
        displayBlockDetails(genesisBlock);

        // Display the updated ledger
        displayLedger(ledgerData);
    }

    public static String calculatePreviousHash(String ledgerData) {
        StringTokenizer blockTokenizer = new StringTokenizer(ledgerData, "|");
        String lastBlockData = null;
    
        // Iterate through blocks to get the last block's data
        while (blockTokenizer.hasMoreTokens()) {
            lastBlockData = blockTokenizer.nextToken();
        }
    
        if (lastBlockData != null) {
            // Parse the last block's data to obtain the current hash
            String[] lastBlockFields = lastBlockData.split(",");
            return lastBlockFields[4]; // Assuming the current hash is at index 4
        } else {
            
            return "0"; // or any default value you prefer
        }
    }


    public static String readLedgerDataFromFile(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            StringBuilder ledgerData = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                ledgerData.append(line);
            }
            reader.close();
            return ledgerData.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void writeLedgerDataToFile(String fileName, String data) {
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void displayLedger(String ledgerData) {
        StringTokenizer blockTokenizer = new StringTokenizer(ledgerData, "|");
        String[] fields = {"Blockchain id", "Transaction id", "User id", "Previous hash","Current hash ", "Timestamp"};
        int blockCount = 1;
    
        while (blockTokenizer.hasMoreTokens()) {
           
            System.out.println("--------------------------------------------");
            System.out.println("Block " + blockCount);
            String blockData = blockTokenizer.nextToken();
            StringTokenizer fieldTokenizer = new StringTokenizer(blockData, ",");
            int i=0;
            while (fieldTokenizer.hasMoreTokens()) {
                String field = fieldTokenizer.nextToken();
                String[] keyValue = field.split(" "); 
                for (String pair : keyValue) {
                    System.out.println( fields[i] +  " - " + pair);
                    i++;
                }
            }
            i = 0;
            blockCount++;
        }
    }   
    
    public static void displayBlockDetails(Ledger block) {
        System.out.println("--------------------------------------------");
        System.out.println("Genesis Block");
        System.out.println("Blockchain ID: " + block.blockchainId);
        System.out.println("Transaction ID: " + block.transactionId);
        System.out.println("User ID: " + block.userId);
        System.out.println("Previous Hash: " + block.prevHash);
        System.out.println("Current Hash: " + block.currHash);
        System.out.println("Timestamp: " + block.timestamp);
    }
       
    public static String getCurrentTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        return dateFormat.format(new Date());
    }
 
    public static int generateRandomBlockchainId() {
        return new Random().nextInt(1000) + 1;
    }
    
}

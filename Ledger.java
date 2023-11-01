import java.io.*;
import java.util.StringTokenizer;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Scanner;

public class Ledger {
    public static void main(String[] args) {
        String ledgerData = readLedgerDataFromFile("ledger.txt");

        // Get user input for new block data
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter transaction ID: ");
        int transactionId = scanner.nextInt();
        System.out.print("Enter user ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Enter previous hash: ");
        String prevHash = scanner.nextLine().trim();
        System.out.print("Enter current hash: ");
        String currHash = scanner.nextLine().trim();
        String timestamp = getCurrentTimestamp();

        // Generate a random blockchain ID
        int blockchainId = generateRandomBlockchainId();

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

        // Display the updated ledger
        displayLedger(ledgerData);
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
        System.out.println("Blockchain ID  | Transaction ID | User ID |\t \t \t Previous Hash \t \t \t|\t \t \t Current Hash \t \t \t| Timestamp");
        StringTokenizer blockTokenizer = new StringTokenizer(ledgerData, "|");
    
        while (blockTokenizer.hasMoreTokens()) {
            String blockData = blockTokenizer.nextToken();
            StringTokenizer fieldTokenizer = new StringTokenizer(blockData, ",");
    
            while (fieldTokenizer.hasMoreTokens()) {
                String field = fieldTokenizer.nextToken();
                // Add spaces between fields
                System.out.print(String.format("%-16s", field) + " ");
            }
            System.out.println();
        }
    }

    public static String getCurrentTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }

    public static int generateRandomBlockchainId() {
        return new Random().nextInt(1000) + 1;
    }
}
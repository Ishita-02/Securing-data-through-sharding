package com.example.cipherPackage;
import java.io.*;
import java.util.Scanner;

public class CaesarCipher {
    private static final String ENCRYPTED_FOLDER = "encryptedFiles";
    private static final String DECRYPTED_FOLDER = "decryptedFiles";

    public void encryptFile(String inputFile, int userId, int key) {
        String outputFile = ENCRYPTED_FOLDER + File.separator + "encryptedFileUser" + userId + ".txt";
        encryptOrDecryptFile(inputFile, outputFile, key);
    }

    public void decryptFile(int userId, int key) {
        String inputFile = "mergedFileUser" + userId + ".txt";
        String outputFile = DECRYPTED_FOLDER + File.separator + "decryptedFileUser" + userId + ".txt";
        encryptOrDecryptFile(inputFile, outputFile, 26 - key);
    }

    private void encryptOrDecryptFile(String inputFile, String outputFile, int key) {
        try (FileReader fileReader = new FileReader(inputFile);
             FileWriter fileWriter = new FileWriter(outputFile)) {

            int character;
            while ((character = fileReader.read()) != -1) {
                if (Character.isLetter(character)) {
                    char processedChar = encryptOrDecryptCharacter((char) character, key);
                    fileWriter.write(processedChar);
                } else {
                    fileWriter.write(character);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private char encryptOrDecryptCharacter(char character, int key) {
        if (Character.isLetter(character)) {
            char processedChar = (char) (character + key);
            if ((Character.isUpperCase(character) && processedChar > 'Z') ||
                (Character.isLowerCase(character) && processedChar > 'z')) {
                processedChar -= 26;
            }
            return processedChar;
        }
        return character;
    }

    public static void main(String[] args) {
        // Example usage
        CaesarCipher caesarCipher = new CaesarCipher();
        Scanner sc = new Scanner(System.in);
        int userId = sc.nextInt();
        int encryptionKey = 3;

        // Encrypt file
        String inputFile = "personalDetailsUser" + userId + ".txt";
        caesarCipher.encryptFile(inputFile, userId, encryptionKey);

        // Decrypt file
        caesarCipher.decryptFile(userId, encryptionKey);
    }
}


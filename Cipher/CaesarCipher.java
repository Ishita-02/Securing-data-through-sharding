package Cipher;
import java.io.*;

public class CaesarCipher {
    public void encryptFile(String inputFile, String outputFile, int key) {
        try (FileReader fileReader = new FileReader(inputFile);
             FileWriter fileWriter = new FileWriter(outputFile)) {

            int character;
            while ((character = fileReader.read()) != -1) {
                if (Character.isLetter(character)) {
                    char encryptedChar = encryptCharacter((char) character, key);
                    fileWriter.write(encryptedChar);
                } else {
                    fileWriter.write(character);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void decryptFile(String inputFile, String outputFile, int key) {
        encryptFile(inputFile, outputFile, 26 - key);
    }

    private char encryptCharacter(char character, int key) {
        if (Character.isLetter(character)) {
            char encryptedChar = (char) (character + key);
            if ((Character.isUpperCase(character) && encryptedChar > 'Z') ||
                (Character.isLowerCase(character) && encryptedChar > 'z')) {
                encryptedChar -= 26;
            }
            return encryptedChar;
        }
        return character;
    }
}

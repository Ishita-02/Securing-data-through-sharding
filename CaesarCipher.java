import java.io.*;

public class CaesarCipher {

    public static void main(String[] args) {
        String inputFilePath = "sampleData.txt";
        String encryptFilePath = "encryptedFile.txt";
        String decryptFilePath = "decryptedFile.txt";
        int key = 3; 

        encryptFile(inputFilePath, encryptFilePath, key);
        decryptFile(encryptFilePath, decryptFilePath, key);
    }

    public static void encryptFile(String inputFile, String outputFile, int key) {
        try (FileReader fileReader = new FileReader(inputFile);
             FileWriter fileWriter = new FileWriter(outputFile)) {

            int character;
            while ((character = fileReader.read()) != -1) {
                if (Character.isLetter(character)) {
                    char encryptedChar = (char) (character + key);
                    if ((Character.isUpperCase((char) character) && encryptedChar > 'Z') ||
                            (Character.isLowerCase((char) character) && encryptedChar > 'z')) {
                        encryptedChar -= 26;
                    }
                    fileWriter.write(encryptedChar);
                } else {
                    fileWriter.write(character);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void decryptFile(String inputFile, String outputFile, int key) {
        encryptFile(inputFile, outputFile, 26 - key);
    }
}
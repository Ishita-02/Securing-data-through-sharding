package Cipher; 
import java.io.FileWriter; 
import java.io.IOException; 
import java.util.Scanner;
 public class PersonalDetailsToFile{ public static void main(String[] args) { 
     Scanner scanner = new Scanner(System.in); 
     System.out.println("Enter your details:"); 
     System.out.print("Name: "); 
     String name = scanner.nextLine(); 
     System.out.print("Age: "); 
     int age = scanner.nextInt(); 
     scanner.nextLine(); 
     // Consume the newline character 
     System.out.print("Address: "); 
     String address = scanner.nextLine(); 
     System.out.print("Phone Number: "); 
     String phoneNumber = scanner.nextLine();
     
     // Create a filename for the text file 
     String fileName = "personal_details.txt"; 
     try { FileWriter fileWriter = new FileWriter(fileName); // Write the personal details to the file 
         fileWriter.write("Name: " + name + "\n"); 
         fileWriter.write("Age: " + age + "\n"); 
         fileWriter.write("Address: " + address + "\n"); 
         fileWriter.write("Phone Number: " + phoneNumber + "\n"); 
         
         // Close the file 
         fileWriter.close(); 
         System.out.println("Personal details saved to " + fileName); } 
         catch (IOException e) { System.out.println("An error occurred while writing to the file: " + e.getMessage()); 
            }
        } 
    }
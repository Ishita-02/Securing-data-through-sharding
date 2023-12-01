package com.example.UserManagerPackage;
import java.io.*;
import java.util.*;

public class UserManager {

    private static HashMap<String, UserInfo> userCredentials = new HashMap<>();
    private static String dataFilePath = "user_credentials.txt";
    private static int nextUserId = 1;

    private static class UserInfo {
        String username;
        String password;
        int userId;

        UserInfo(String username, String password, int userId) {
            this.username = username;
            this.password = password;
            this.userId = userId;
        }
    }

    public static void registerUser(String username, String password) throws IOException {
        int userId = getNextUserId();
        userCredentials.put(username, new UserInfo(username, password, userId));
        saveData();
        System.out.println("User registration successful. UserId: " + userId);
    }

    public static boolean loginUser(String username, String password) throws IOException {
        if (userCredentials.containsKey(username)) {
            UserInfo userInfo = userCredentials.get(username);
            if (password.equals(userInfo.password)) {
                System.out.println("Login successful. UserId: " + userInfo.userId);
                return true;
            } else {
                System.out.println("Incorrect password.");
                return false;
            }
        } else {
            System.out.println("User not found. Please register or try again with a different username.");
            return false;
        }
    }

    private static void saveData() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(dataFilePath, true))) {
            for (Map.Entry<String, UserInfo> entry : userCredentials.entrySet()) {
                UserInfo userInfo = entry.getValue();
                writer.println(userInfo.userId + "," + userInfo.username + "," + userInfo.password);
            }
        }
    }

    public static void loadData() throws IOException {
        userCredentials.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) { // Ensure there are enough parts
                    int userId = Integer.parseInt(parts[0]);
                    String username = parts[1];
                    String password = parts[2];
                    userCredentials.put(username, new UserInfo(username, password, userId));
                    nextUserId = Math.max(nextUserId, userId + 1);
                }
            }
        }
    }

    private static int getNextUserId() {
        return nextUserId++;
    }

    // public static void main(String[] args) {
    //     UserManager userManager = new UserManager();
    //     Scanner scanner = new Scanner(System.in);

    //     try {
    //         userManager.loadData();

    //         System.out.println("Enter 'register' to register a new user or 'login' to log in:");
    //         String choice = scanner.nextLine();

    //         if (choice.equals("register")) {
    //             System.out.println("Enter username:");
    //             String username = scanner.nextLine();

    //             System.out.println("Enter password:");
    //             String password = scanner.nextLine();

    //             userManager.registerUser(username, password);
    //         } else if (choice.equals("login")) {
    //             System.out.println("Enter username:");
    //             String username = scanner.nextLine();

    //             System.out.println("Enter password:");
    //             String password = scanner.nextLine();

    //             userManager.loginUser(username, password);
    //         } else {
    //             System.out.println("Invalid choice. Please enter 'register' or 'login'.");
    //         }
    //     } catch (IOException e) {
    //         System.out.println("Error accessing user data file.");
    //         e.printStackTrace();
    //     }
    // }
}

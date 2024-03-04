package com.veritech;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class User {
    private String userID;
    private String userPIN;
    private double accountBalance;

    public User(String userID, String userPIN, double accountBalance) {
        this.userID = userID;
        this.userPIN = userPIN;
        this.accountBalance = accountBalance;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserPIN() {
        return userPIN;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double balance) {
        this.accountBalance = balance;
    }
}

class ATM {
    private Map<String, User> users;

    public ATM() {
        users = new HashMap<>();
        // Pre-populating some dummy user data
        users.put("123456", new User("123456", "1234", 1000.0));
        users.put("789012", new User("789012", "5678", 500.0));
    }

    public boolean authenticateUser(String userID, String userPIN) {
        if (users.containsKey(userID)) {
            User user = users.get(userID);
            return user.getUserPIN().equals(userPIN);
        }
        return false;
    }

    public double checkBalance(String userID) {
        if (users.containsKey(userID)) {
            return users.get(userID).getAccountBalance();
        }
        return -1; // User not found
    }

    public boolean withdraw(String userID, double amount) {
        if (users.containsKey(userID)) {
            User user = users.get(userID);
            if (user.getAccountBalance() >= amount) {
                user.setAccountBalance(user.getAccountBalance() - amount);
                return true; // Withdrawal successful
            }
        }
        return false; // Insufficient funds or user not found
    }

    public void deposit(String userID, double amount) {
        if (users.containsKey(userID)) {
            User user = users.get(userID);
            user.setAccountBalance(user.getAccountBalance() + amount);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ATM atm = new ATM();

        System.out.println("Welcome to the ATM!");

        // Authentication
        System.out.print("Enter User ID: ");
        String userID = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        if (atm.authenticateUser(userID, pin)) {
            System.out.println("Authentication successful!");
            boolean exit = false;
            while (!exit) {
                System.out.println("\n1. Check Balance\n2. Withdraw\n3. Deposit\n4. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        double balance = atm.checkBalance(userID);
                        if (balance != -1) {
                            System.out.println("Your balance: $" + balance);
                        } else {
                            System.out.println("User not found.");
                        }
                        break;
                    case 2:
                        System.out.print("Enter amount to withdraw: $");
                        double withdrawAmount = scanner.nextDouble();
                        if (atm.withdraw(userID, withdrawAmount)) {
                            System.out.println("Withdrawal successful!");
                        } else {
                            System.out.println("Withdrawal failed. Insufficient funds.");
                        }
                        break;
                    case 3:
                        System.out.print("Enter amount to deposit: $");
                        double depositAmount = scanner.nextDouble();
                        atm.deposit(userID, depositAmount);
                        System.out.println("Deposit successful!");
                        break;
                    case 4:
                        exit = true;
                        System.out.println("Thank you for using the ATM!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } else {
            System.out.println("Authentication failed. Invalid user ID or PIN.");
        }

        scanner.close();
    }
}

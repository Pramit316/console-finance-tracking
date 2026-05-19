package controller;

import entity.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TransactionController {

    private final Scanner sc = new Scanner(System.in);
    static List<Transaction> transactionList = new ArrayList<>();

    public void addNewTransaction() {

        Transaction t = new Transaction();

        System.out.println("\n----------------------------------------");
        System.out.println("           ADD NEW TRANSACTION");
        System.out.println("----------------------------------------");

        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        t.setId(id);

        System.out.print("Enter Title: ");
        String title = sc.nextLine();
        t.setTitle(title);

        System.out.print("Enter Amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();
        t.setAmount(amount);

        System.out.print("Enter Category: ");
        String category = sc.nextLine();
        t.setCategory(category);

        LocalDate currentDate = LocalDate.now();
        t.setDate(currentDate);

        System.out.print("Enter Description: ");
        String description = sc.nextLine();
        t.setDescription(description);

        transactionList.add(t);

        System.out.println("\nTransaction added successfully!");
    }

    public void viewAllTransaction() {

        System.out.println("\n----------------------------------------");
        System.out.println("             TRANSACTION LIST");
        System.out.println("----------------------------------------");

        if (transactionList.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        System.out.printf("%-5s %-15s %-10s %-15s %-15s %-20s%n",
                "ID", "TITLE", "AMOUNT", "CATEGORY", "DATE", "DESCRIPTION");

        System.out.println("--------------------------------------------------------------------------------");

        for (Transaction t : transactionList) {
            System.out.printf("%-5d %-15s %-10.2f %-15s %-15s %-20s%n",
                    t.getId(),
                    t.getTitle(),
                    t.getAmount(),
                    t.getCategory(),
                    t.getDate(),
                    t.getDescription());
        }
    }
}
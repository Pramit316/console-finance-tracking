package controller;

import entity.Transaction;
import entity.TransactionType;
import service.TransactionService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class TransactionController {

    private final Scanner sc = new Scanner(System.in);
    private final TransactionService transactionService = new TransactionService();

    public void addNewTransaction() {

        Transaction t = new Transaction();

        int id = transactionService.generateId();
        t.setId(id);

        System.out.println("\n----------------------------------------");
        System.out.println("           ADD NEW TRANSACTION");
        System.out.println("----------------------------------------");

        System.out.println("Generated Transaction ID: " + id);

        System.out.print("Enter Title: ");
        String title = sc.nextLine();
        t.setTitle(title);

        System.out.print("Enter Amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();
        t.setAmount(amount);

        System.out.println("Select Category: ");
        System.out.println("1. Income");
        System.out.println("2. Expense");
        System.out.print("Enter choice: ");
        int category = sc.nextInt();
        sc.nextLine();

        t.setCategory(String.valueOf(category == 1 ? TransactionType.INCOME : TransactionType.EXPENSE));

        LocalDate currentDate = LocalDate.now();
        t.setDate(currentDate);

        System.out.print("Enter Description: ");
        String description = sc.nextLine();
        t.setDescription(description);

        transactionService.addTransaction(t);

        System.out.println("\nTransaction added successfully!");
    }

    public void transactionMenu() {

        boolean backToMainMenu = false;

        while (!backToMainMenu) {

            System.out.println("\n========================================");
            System.out.println("            TRANSACTION MENU");
            System.out.println("========================================");
            System.out.println("1. View All Transactions");
            System.out.println("2. Find Transaction By ID");
            System.out.println("3. Delete Transaction");
            System.out.println("4. Update Transaction");
            System.out.println("5. Back To Main Menu");
            System.out.println("----------------------------------------");
            System.out.print("Enter your choice: ");

            if (!sc.hasNextInt()) {
                System.out.println("\nInvalid input. Please enter a number.");
                sc.nextLine();
                pause();
                continue;
            }

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    viewAllTransaction();
                    pause();
                    break;

                case 2:
                    showFindTransactionByIdScreen();
                    pause();
                    break;

                case 3:
                    showDeleteTransactionScreen();
                    pause();
                    break;

                case 4:
                    showUpdateTransactionScreen();
                    pause();
                    break;

                case 5:
                    backToMainMenu = true;
                    break;

                default:
                    System.out.println("\nInvalid choice. Please choose between 1 and 5.");
                    pause();
                    break;
            }
        }
    }

    public void viewAllTransaction() {

        List<Transaction> transactionList = transactionService.getAllTransactions();

        System.out.println("\n----------------------------------------");
        System.out.println("             TRANSACTION LIST");
        System.out.println("----------------------------------------");

        if (transactionList.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        consolePrint(transactionList);

    }

    public void showFindTransactionByIdScreen() {

        System.out.println("\n----------------------------------------");
        System.out.println("         FIND TRANSACTION BY ID");
        System.out.println("----------------------------------------");

        System.out.print("Enter Transaction ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        Transaction t = transactionService.findTransactionById(id);

        System.out.println("\nSearching transaction with ID: " + id);

        consolePrint(t);
    }

    public void showDeleteTransactionScreen() {

        System.out.println("\n----------------------------------------");
        System.out.println("           DELETE TRANSACTION");
        System.out.println("----------------------------------------");

        System.out.print("Enter Transaction ID to delete: ");
        int id = sc.nextInt();
        sc.nextLine();

        // transactionService.deleteTransaction(id);

        System.out.println("\nDelete request received for transaction ID: " + id);
    }

    public void showUpdateTransactionScreen() {

        System.out.println("\n----------------------------------------");
        System.out.println("           UPDATE TRANSACTION");
        System.out.println("----------------------------------------");

        System.out.print("Enter Transaction ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.println("\nWhat do you want to update?");
        System.out.println("1. Title");
        System.out.println("2. Amount");
        System.out.println("3. Category");
        System.out.println("4. Description");
        System.out.println("5. Update Full Transaction");
        System.out.println("6. Cancel");
        System.out.print("Enter your choice: ");

        int updateChoice = sc.nextInt();
        sc.nextLine();

        // transactionService.updateTransaction(id, updatedTransaction);

        System.out.println("\nUpdate request received for transaction ID: " + id);
        System.out.println("Selected update option: " + updateChoice);
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        sc.nextLine();
    }

    public void consolePrint(Transaction t){
        System.out.printf("%-5s %-15s %-10s %-15s %-15s %-20s%n",
                "ID", "TITLE", "AMOUNT", "CATEGORY", "DATE", "DESCRIPTION");

        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("%-5d %-15s %-10.2f %-15s %-15s %-20s%n",
                t.getId(),
                t.getTitle(),
                t.getAmount(),
                t.getCategory(),
                t.getDate(),
                t.getDescription());
    }

    public void consolePrint(List<Transaction> transactionList){
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
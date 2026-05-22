package controller;

import entity.Transaction;
import entity.TransactionCategory;
import entity.TransactionType;
import exceptions.InvalidAmountException;
import exceptions.InvalidDateException;
import exceptions.InvalidTransactionTypeException;
import exceptions.TransactionNotFoundException;
import service.TransactionService;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class TransactionController {

    private final Scanner sc = new Scanner(System.in);
    private final TransactionService transactionService;
    TransactionCategory[] categories = TransactionCategory.values();

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    public void addNewTransaction() {

        Transaction t = new Transaction();

        int id = transactionService.generateId();
        t.setId(id);

        try{
            System.out.println("\n----------------------------------------");
            System.out.println("           ADD NEW TRANSACTION");
            System.out.println("----------------------------------------");

            //generated id
            System.out.println("Generated Transaction ID: " + id);

            //tile section
            System.out.print("Enter Title: ");
            String title = sc.nextLine();
            t.setTitle(title);

            //amount section
            System.out.print("Enter Amount: ");
            double amount = sc.nextDouble();
            sc.nextLine();
            t.setAmount(amount);

            //category section
            System.out.println("Select Category: ");

            for(int i = 0; i<categories.length; i++){
                System.out.println((i+1) + ". " + categories[i]);
            }

            int choice = sc.nextInt();
            sc.nextLine();

            TransactionCategory selectedCategory = categories[choice -1];
            System.out.println("Selected: " + selectedCategory);
            t.setCategory(selectedCategory);

            //type section
            System.out.println("Select Type: ");
            System.out.println("1. Income");
            System.out.println("2. Expense");
            System.out.print("Enter choice: ");
            int type = sc.nextInt();
            sc.nextLine();

            t.setType(type == 1 ? TransactionType.INCOME : TransactionType.EXPENSE);

            //date section
            System.out.println("Enter the date in yyyy-mm-dd: ");
            LocalDate currentDate = parseDate(sc.nextLine());
            t.setDate(currentDate);

            //description section
            System.out.print("Enter Description: ");
            String description = sc.nextLine();
            t.setDescription(description);

            transactionService.addTransaction(t);

            System.out.println("\nTransaction added successfully!");
        } catch (InvalidDateException e){
            System.out.println("There was something wrong. \n" + e);
        } catch (Exception e) {
            System.out.println("\nSomething went wrong. Please enter valid input");
            pause();
        }
    }

    private LocalDate parseDate(String s) {
        try{
            return LocalDate.parse(s);
        } catch (DateTimeException e){
            throw new InvalidDateException("Invalid date format. Please enter date in yyyy-MM-dd format.");
        }
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

        try{
            System.out.print("Enter Transaction ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            Transaction t = transactionService.findTransactionById(id);

            System.out.println("\nSearching transaction with ID: " + id);

            consolePrint(t);
        } catch (TransactionNotFoundException e){
            System.out.println("\nError: " + e.getMessage());
        }
    }

    public void showDeleteTransactionScreen() {

        System.out.println("\n----------------------------------------");
        System.out.println("           DELETE TRANSACTION");
        System.out.println("----------------------------------------");

        System.out.print("Enter Transaction ID to delete: ");
        int id = sc.nextInt();
        sc.nextLine();

        transactionService.deleteTransaction(id);

        System.out.println("\nDelete request received for transaction ID: " + id);
    }

    public void showUpdateTransactionScreen() {

        System.out.println("\n----------------------------------------");
        System.out.println("           UPDATE TRANSACTION");
        System.out.println("----------------------------------------");

       try{
           System.out.print("Enter Transaction ID to update: ");
           int id = sc.nextInt();
           sc.nextLine();

           System.out.println("\nWhat do you want to update?");
           System.out.println("1. Title");
           System.out.println("2. Amount");
           System.out.println("3. Type");
           System.out.println("4. Description");
           System.out.println("5. Update Date");
           System.out.println("6. Update Category");
           System.out.println("7. Update Full Transaction");
           System.out.println("8. Cancel");
           System.out.print("Enter your choice: ");

           int updateChoice = sc.nextInt();
           sc.nextLine();

           switch (updateChoice){
               case 1:
                   System.out.print("Enter the new title: ");
                   String title = sc.nextLine();
                   transactionService.updateTitle(id, title);
                   break;

               case 2:
                   System.out.print("Enter the new amount: ");
                   double amount = sc.nextDouble();
                   transactionService.updateAmount(id, amount);
                   break;

               case 3:
                   System.out.print("Select the Type: ");
                   System.out.println("1. Income");
                   System.out.println("2. Expense");
                   System.out.print("Enter your choice");
                   int input = sc.nextInt();
                   sc.nextLine();

                   transactionService.updateType(id, input == 1 ? TransactionType.INCOME : TransactionType.EXPENSE);
                   break;

               case 4:
                   String description = sc.nextLine();
                   transactionService.updateDescription(id, description);
                   break;

               case 5:
                   LocalDate date = LocalDate.parse(sc.nextLine());
                   transactionService.updateDate(id, date);
                   break;

               case 6:
                   System.out.println("Select Category: ");

                   for(int i = 0; i<categories.length; i++){
                       System.out.println((i+1) + ". " + categories[i]);
                   }

                   int choice = sc.nextInt();
                   sc.nextLine();

                   TransactionCategory selectedCategory = categories[choice -1];
                   System.out.println("Selected: " + selectedCategory);
                   transactionService.updateCategory(id, selectedCategory);

               case 7:
                   updateFullTransaction(id);
           }

           System.out.println("\nUpdate request received for transaction ID: " + id);
           System.out.println("Selected update option: " + updateChoice);
       } catch(InvalidAmountException | TransactionNotFoundException | InvalidTransactionTypeException exception){
           System.out.println("\nError: " + exception.getMessage());
       } catch (Exception e){
           System.out.println("\nSomething went wrong. Please enter the valid input.");
       }
    }

    private void updateFullTransaction(int id) {

        Transaction t = new Transaction();
        t.setId(id);

        System.out.println("\n----------------------------------------");
        System.out.println("           UPDATE TRANSACTION");
        System.out.println("----------------------------------------");

        System.out.println("Generated Transaction ID: " + id);

        System.out.print("Enter Title: ");
        String title = sc.nextLine();
        t.setTitle(title);

        System.out.print("Enter Amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();
        t.setAmount(amount);

        System.out.println("Select Type: ");
        System.out.println("1. Income");
        System.out.println("2. Expense");
        System.out.print("Enter choice: ");
        int type = sc.nextInt();
        sc.nextLine();

        t.setType(type == 1 ? TransactionType.INCOME : TransactionType.EXPENSE);

        System.out.print("Enter the date in yyyy-MM-dd format: ");
        LocalDate currentDate = parseDate(sc.nextLine());
        t.setDate(currentDate);

        System.out.print("Enter Description: ");
        String description = sc.nextLine();
        t.setDescription(description);

        transactionService.updateEntireTransaction(t);

        System.out.println("\nTransaction added successfully!");
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        sc.nextLine();
    }

    // Single result in a table
    public static void consolePrint(Transaction t) {
        System.out.printf("%-5s %-25s %-15s %-15s %-20s %-15s %-50s%n",
                "ID", "TITLE", "AMOUNT", "TYPE", "CATEGORY", "DATE", "DESCRIPTION");

        System.out.println("---------------------------------------------------------------------------------------------------------------");

        System.out.printf("%-5d %-25s %-15.2f %-15s %-20s %-15s %-50s%n",
                t.getId(),
                t.getTitle(),
                t.getAmount(),
                t.getType(),
                t.getCategory(),
                t.getDate(),
                t.getDescription());
    }


    // List of results in a table
    public static void consolePrint(List<Transaction> transactionList) {
        System.out.printf("%-5s %-25s %-15s %-15s %-20s %-15s %-50s%n",
                "ID", "TITLE", "AMOUNT", "TYPE", "CATEGORY", "DATE", "DESCRIPTION");

        System.out.println("---------------------------------------------------------------------------------------------------------------");

        for (Transaction t : transactionList) {
            System.out.printf("%-5d %-25s %-15.2f %-15s %-20s %-15s %-50s%n",
                    t.getId(),
                    t.getTitle(),
                    t.getAmount(),
                    t.getType(),
                    t.getCategory(),
                    t.getDate(),
                    t.getDescription());
        }
    }
}
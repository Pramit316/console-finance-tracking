package controller;

import entity.Transaction;
import entity.TransactionType;
import service.TransactionService;

import java.time.LocalDate;
import java.util.Scanner;

public class SearchFilterController {

    private final Scanner sc = new Scanner(System.in);
    TransactionService transactionService;

    public SearchFilterController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    public void searchFilterMenu() {
        boolean back = false;

        while (!back) {
            System.out.println("\n========================================");
            System.out.println("          SEARCH AND FILTER MENU");
            System.out.println("========================================");
            System.out.println("1. Search by Category");
            System.out.println("2. Search by Type");
            System.out.println("3. Search by Date");
            System.out.println("4. Search by Amount Range");
            System.out.println("5. View Transactions Sorted by Date");
            System.out.println("6. View Transactions Sorted by Amount");
            System.out.println("7. Back to Transaction Menu");
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
                    searchByCategoryScreen();
                    pause();
                    break;

                case 2:
                    searchByTypeScreen();
                    pause();
                    break;

                case 3:
                    searchByDateScreen();
                    pause();
                    break;

                case 4:
                    searchByAmountRangeScreen();
                    pause();
                    break;

                case 5:
                    sortedByDateScreen();
                    pause();
                    break;

                case 6:
                    sortedByAmountScreen();
                    pause();
                    break;

                case 7:
                    back = true;
                    break;

                default:
                    System.out.println("\nInvalid choice. Please choose between 1 and 7.");
                    pause();
                    break;
            }
        }
    }

    private void sortedByAmountScreen() {
        System.out.println("SORTED BY AMOUNT");
        TransactionController.consolePrint(transactionService.sortedByAmountScreen());
    }

    private void sortedByDateScreen() {
        System.out.println("SORTED BY DATE");
        TransactionController.consolePrint(transactionService.sortedByDate());
    }

    private void searchByAmountRangeScreen() {
        System.out.println("SEARCH BY AMOUNT RANGE");
        System.out.print("Enter your lower limit: ");
        double lower = sc.nextDouble();
        System.out.println("Enter your upper limit: ");
        double upper = sc.nextDouble();

        TransactionController.consolePrint(transactionService.searchByAmountRange(lower, upper));
    }

    private void searchByDateScreen() {
        System.out.println("SEARCH BY DATE");
        System.out.print("Enter the date yyyy-MM-dd: ");

        String dateInput = sc.nextLine();

        LocalDate date = LocalDate.parse(dateInput);

        TransactionController.consolePrint(transactionService.searchByDate(date));
    }

    private void searchByTypeScreen() {
        System.out.println("Select the type");
        System.out.println("1. Income");
        System.out.println("2. Expense");
        System.out.print("Enter your choice: ");
        int input = sc.nextInt();
        TransactionController.consolePrint(transactionService.searchByType(input == 1 ? TransactionType.INCOME : TransactionType.EXPENSE));
    }

    private void searchByCategoryScreen() {

    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        sc.nextLine();
    }
}
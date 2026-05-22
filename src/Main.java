import controller.ReportController;
import controller.SearchFilterController;
import controller.TransactionController;
import entity.Transaction;
import service.AutoSaveService;
import service.FileService;
import service.ReportService;
import service.TransactionService;

import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        TransactionService transactionService = new TransactionService();
        ReportService reportService = new ReportService(transactionService);
        FileService fileService = new FileService();

        //loads the currently saved transaction
        List<Transaction> loadedTransaction = fileService.loadTransaction();

        //sets the currently saved transaction to in the transaction service
        transactionService.setTransactionList(loadedTransaction);

        //updates the id to match with already saved data id
        transactionService.updateNextId();

        AutoSaveService autoSaveService = new AutoSaveService(fileService, transactionService);
        autoSaveService.startAutoSave();

        TransactionController transactionController = new TransactionController(transactionService);
        SearchFilterController searchFilterController = new SearchFilterController(transactionService);
        ReportController reportController = new ReportController(reportService);

        boolean running = true;

        while (running) {
            showMenu();

            System.out.print("Enter your choice: ");

            if (!sc.hasNextInt()) {
                System.out.println("\nInvalid input. Please enter a number.");
                sc.nextLine();
                pause();
                continue;
            }

            int input = sc.nextInt();
            sc.nextLine();

            switch (input) {
                case 1:
                    transactionController.addNewTransaction();
                    pause();
                    break;

                case 2:
                    transactionController.transactionMenu();
                    pause();
                    break;

                case 3:
                    searchFilterController.searchFilterMenu();
                    pause();
                    break;

                case 4:
                    reportController.reportMenu();
                    pause();
                    break;

                case 5:
                    System.out.println("Do you want to save before exiting? (y/n)");
                    String option = sc.next();
                    sc.nextLine();

                    if(option.equalsIgnoreCase("y")){
                        fileService.saveTransactions(transactionService.getAllTransactions());
                    }

                    autoSaveService.stopAutoSave();

                    System.out.println("\nThank you for using Console Finance Tracker.");
                    System.out.println("Exiting...");
                    running = false;
                    break;

                default:
                    System.out.println("\nInvalid choice. Please choose between 1 and 3.");
                    pause();
                    break;
            }
        }

        sc.close();
    }

    public static void showMenu() {
        System.out.println("\n========================================");
        System.out.println("        CONSOLE FINANCE TRACKER");
        System.out.println("========================================");
        System.out.println("1. Add New Transaction");
        System.out.println("2. Transactions Menu");
        System.out.println("3. Search and Filter");
        System.out.println("4. Transaction Report");
        System.out.println("5. Exit");
        System.out.println("----------------------------------------");
    }

    public static void pause() {
        System.out.println("\nPress Enter to continue...");
        sc.nextLine();
    }
}
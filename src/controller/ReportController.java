package controller;

import service.ReportService;
import java.util.Scanner;

public class ReportController {

    Scanner sc = new Scanner(System.in);
    ReportService reportService;

    public ReportController(ReportService reportService){
        this.reportService = reportService;
    }

    public void reportMenu() {
        boolean back = false;

        while(!back){
            System.out.println("\n========================================");
            System.out.println("          REPORT GENERATION MENU");
            System.out.println("========================================");
            System.out.println("1. Total Income");
            System.out.println("2. Total Expense");
            System.out.println("3. Current Balance");
            System.out.println("4. Category-wise Summary");
            System.out.println("5. Monthly Summary");
            System.out.println("6. Highest Income");
            System.out.println("7. Highest Expense");
            System.out.println("8. Average Expense");
            System.out.println("9. Average Income");
            System.out.println("10. Back to main menu");
            System.out.println("----------------------------------------");
            System.out.print("Enter your choice: ");

            if(!sc.hasNextInt()){
                System.out.println("Enter a valid number");
                pause();
            }

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    reportService.totalIncome();
                    break;

                case 2:
                    reportService.totalExpense();
                    break;

                case 3:
                    reportService.currentBalance();
                    break;

                case 4:
                    reportService.categoryWiseSummary();
                    break;

                case 5:
                    reportService.monthlySummary();
                    break;

                case 6:
                    reportService.highestIncome();

                case 7:
                    reportService.highestExpense();

                case 8:
                    reportService.averageExpense();
                    break;

                case 9:
                    reportService.averageIncome();
                    break;

                case 10:
                    return;

                default:
                    System.out.println("Invalid option! Try again.");
            }
        }
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        sc.nextLine();
    }
}

package controller;

import dto.MonthlySummary;
import service.ReportService;

import java.util.List;
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
                sc.nextLine();
                pause();
                continue;
            }

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Total Income: " + reportService.totalIncome());
                    break;

                case 2:
                    System.out.println("Total Expense: " + reportService.totalExpense());
                    break;

                case 3:
                    System.out.println("Current Balance: " + reportService.currentBalance());
                    break;

                case 4:
                    reportService.categoryWiseSummary();
                    break;

                case 5:
                    printMonthlySummary();
                    break;

                case 6:
                    TransactionController.consolePrint(reportService.highestIncome());
                    break;

                case 7:
                    TransactionController.consolePrint(reportService.highestExpense());
                    break;

                case 8:
                    System.out.println("Average Expense: " + reportService.averageExpense());
                    break;

                case 9:
                    System.out.println("Average Income: " + reportService.averageIncome());
                    break;

                case 10:
                    return;

                default:
                    System.out.println("Invalid option! Try again.");
            }
        }
    }

    private void printMonthlySummary() {
        List<MonthlySummary> summaries = reportService.monthlySummary();

        if (summaries.isEmpty()) {
            System.out.println("\nNo transactions found.");
            return;
        }

        System.out.println("\n===============================================");
        System.out.println("              MONTHLY SUMMARY");
        System.out.println("===============================================");

        System.out.printf("%-12s %-12s %-12s %-12s%n",
                "Month", "Income", "Expense", "Balance");

        System.out.println("-----------------------------------------------");

        for (MonthlySummary summary : summaries) {
            System.out.printf("%-12s %-12.2f %-12.2f %-12.2f%n",
                    summary.getMonth(),
                    summary.getIncome(),
                    summary.getExpense(),
                    summary.getBalance());
        }

        System.out.println("===============================================");
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        sc.nextLine();
    }
}

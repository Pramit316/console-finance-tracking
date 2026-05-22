package controller;

import dto.CategoryDto;
import dto.MonthlySummary;
import exceptions.TransactionNotFoundException;
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

        try {
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
                        pause();
                        break;

                    case 2:
                        System.out.println("Total Expense: " + reportService.totalExpense());
                        pause();
                        break;

                    case 3:
                        System.out.println("Current Balance: " + reportService.currentBalance());
                        pause();
                        break;

                    case 4:
                        printCategoryWiseSummary();
                        pause();
                        break;

                    case 5:
                        printMonthlySummary();
                        pause();
                        break;

                    case 6:
                        TransactionController.consolePrint(reportService.highestIncome());
                        pause();
                        break;

                    case 7:
                        TransactionController.consolePrint(reportService.highestExpense());
                        pause();
                        break;

                    case 8:
                        System.out.println("Average Expense: " + reportService.averageExpense());
                        pause();
                        break;

                    case 9:
                        System.out.println("Average Income: " + reportService.averageIncome());
                        pause();
                        break;

                    case 10:
                        return;

                    default:
                        System.out.println("Invalid option! Try again.");
                }
            }
        } catch (TransactionNotFoundException e) {
            System.out.println("There was an error!!: \n" + e);
        }
    }

    private void printCategoryWiseSummary() {
        List<CategoryDto> categoryList = reportService.categoryWiseSummary();

        if (categoryList.isEmpty()) {
            System.out.println("\nNo transactions found.");
            return;
        }

        System.out.println("\n===============================================");
        System.out.println("              CATEGORY WISE SUMMARY");
        System.out.println("===============================================");

        System.out.printf("%-12s %-12s %-12s %-12s%n",
                "Category", "Income", "Expense", "Balance");

        System.out.println("-----------------------------------------------");

        for (CategoryDto categoryDto : categoryList) {
            System.out.printf("%-12s %-12.2f %-12.2f %-12.2f%n",
                    categoryDto.getCategory(),
                    categoryDto.getIncome(),
                    categoryDto.getExpense(),
                    categoryDto.getBalance());
        }

        System.out.println("===============================================");
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

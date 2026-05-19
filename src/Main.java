import entity.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static List<Transaction> transactionList = new ArrayList<>();

    public static void main(String[] args) {

        while (true){
            System.out.println("Welcome to Console Based Finance Tracking");
            System.out.println("1. Add new Transaction");
            System.out.println("2. View all Transaction");
            System.out.println("3. Exit\n");

            System.out.println("Enter your choice:");
            int input = sc.nextInt();
            sc.nextLine();

            switch(input){

                case 1:
                    addTransaction();
                    break;

                case 2:
                    viewAllTransaction();
                    System.out.println("Viewed all transaction");
                    break;

                case 3:
                    System.out.println("Exiting....");
                    return;
            }
        }

    }

    static void addTransaction(){
        Transaction t = new Transaction();

        System.out.println("Add new Transaction\n");

        System.out.println("Enter id: ");
        int id = sc.nextInt();
        t.setId(id);
        sc.nextLine();

        System.out.println("Enter title: ");
        String title = sc.nextLine();
        t.setTitle(title);

        System.out.println("Enter amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();
        t.setAmount(amount);

        System.out.println("Enter category: ");
        String category = sc.next();
        t.setCategory(category);

        LocalDate currentDate = LocalDate.now();
        t.setDate(currentDate);

        System.out.println("Enter description: ");
        String description = sc.next();
        t.setDescription(description);

        transactionList.add(t);
    }

    static void viewAllTransaction(){
        System.out.println(transactionList);
    }
}

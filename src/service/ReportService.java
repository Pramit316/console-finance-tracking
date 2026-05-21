package service;

import entity.Transaction;
import entity.TransactionType;
import exceptions.TransactionNotFoundException;

import java.time.Month;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

public class ReportService {

    final TransactionService transactionService;

    public ReportService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public List<Transaction> getTransactionList(){
        return transactionService.getAllTransactions();
    }

    public Transaction highestIncome() {
        List<Transaction> transactionList = getTransactionList();
        return transactionList.stream()
                .filter(x -> x.getType().equals(TransactionType.INCOME))
                .max(Comparator.comparingDouble(Transaction::getAmount))
                .orElseThrow(() -> new TransactionNotFoundException("Could not find a transaction with income"));
    }

    public double averageIncome() {
        List<Transaction> transactionList = getTransactionList();
        return transactionList.stream()
                .filter(x -> x.getType().equals(TransactionType.INCOME))
                .mapToDouble(Transaction::getAmount)
                .average()
                .orElseThrow(() -> new TransactionNotFoundException("Could not find a transaction with income"));
    }

    public double averageExpense() {
        List<Transaction> transactionList = getTransactionList();
        return transactionList.stream()
                .filter(x -> x.getType().equals(TransactionType.EXPENSE))
                .mapToDouble(Transaction::getAmount)
                .average()
                .orElseThrow(() -> new TransactionNotFoundException("Could not find a transaction with Expense"));
    }

    public Transaction highestExpense() {
        List<Transaction> transactionList = getTransactionList();
        return transactionList.stream()
                .filter(x -> x.getType().equals(TransactionType.EXPENSE))
                .max(Comparator.comparingDouble(Transaction::getAmount))
                .orElseThrow(() -> new TransactionNotFoundException("Could not find a transaction with Expense"));
    }

    public void monthlySummary() {
        List<Transaction> transactionList = getTransactionList();
        Map<YearMonth, Double> income = transactionList.stream()
                .filter(x -> x.getType().equals(TransactionType.INCOME))
                .collect(Collectors.groupingBy(
                        t -> YearMonth.from(t.getDate()),
                        Collectors.summingDouble(Transaction::getAmount)
                ));

        Map<YearMonth, Double> expense = transactionList.stream()
                .filter(x -> x.getType().equals(TransactionType.EXPENSE))
                .collect(Collectors.groupingBy(
                        t -> YearMonth.from(t.getDate()),
                        Collectors.summingDouble(Transaction::getAmount)
                ));

        System.out.println("Income: " + income + "\nExpense: " + expense);

        Map<YearMonth, Double> balance = new HashMap<>();

        income.forEach((month, incomeAmount) -> {
            double expenseAmount = expense.getOrDefault(month, 0.0);
            balance.put(month, incomeAmount - expenseAmount);
        });

        expense.forEach(((month, expenseAmount) -> {
            if(!balance.containsKey(month)){
                balance.put(month, 0.0 - expenseAmount);
            }
        } ));

        System.out.println("Income: " + income + "\nExpense: " + expense + "\n Balance: " + balance);
    }

    public void categoryWiseSummary() {
        List<Transaction> transactionList = getTransactionList();
        System.out.println(
                transactionList.stream()
                        .collect(Collectors.groupingBy(Transaction::getType))
        );
    }

    public void currentBalance() {
        double totalIncome = totalIncome();
        double totalExpense = totalExpense();

        double balance = totalIncome - totalExpense;
        System.out.println(balance);
    }

    public double totalExpense() {
        List<Transaction> transactionList = getTransactionList();
        return transactionList.stream()
                .filter(t -> t.getType().equals(TransactionType.EXPENSE)).mapToDouble(Transaction::getAmount).sum();
    }

    public double totalIncome() {
        List<Transaction> transactionList = getTransactionList();
        return transactionList.stream()
                .filter(t-> t.getType().equals(TransactionType.INCOME)).mapToDouble(Transaction::getAmount).sum();
    }
}

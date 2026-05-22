package service;

import dto.CategoryDto;
import dto.MonthlySummary;
import entity.Transaction;
import entity.TransactionCategory;
import entity.TransactionType;
import exceptions.TransactionNotFoundException;

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

    public List<MonthlySummary> monthlySummary() {
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

        List<MonthlySummary> summaries = new ArrayList<>();

        balance.forEach((month, balanceAmount) -> {
            double incomeAmount = income.getOrDefault(month, 0.0);
            double expenseAmount = expense.getOrDefault(month, 0.0);

            summaries.add(new MonthlySummary(month,
               incomeAmount,
               expenseAmount,
               balanceAmount
            ));
        });

        return summaries;
    }

    public List<CategoryDto> categoryWiseSummary() {
        List<Transaction> transactionList = getTransactionList();
        List<CategoryDto> categoryDtos = new ArrayList<>();

        Map<TransactionCategory, Double> balance = new HashMap<>();

        Map<TransactionCategory, Double> income = transactionList.stream()
                .filter(t -> t.getType().equals(TransactionType.INCOME))
                .collect(Collectors.groupingBy(
                        Transaction::getCategory,
                        Collectors.summingDouble(Transaction::getAmount)
                ));


        Map<TransactionCategory, Double> expense = transactionList.stream()
                .filter(t -> t.getType().equals(TransactionType.EXPENSE))
                .collect(Collectors.groupingBy(
                        Transaction::getCategory,
                        Collectors.summingDouble(Transaction::getAmount)
                ));

        income.forEach((category, incomeAmount) -> {
            balance.put(category, incomeAmount - expense.getOrDefault(category, 0.0));
        });

        expense.forEach((category, expenseAmount)->{
            if(!balance.containsKey(category)){
                balance.put(category, 0.0 - expenseAmount);
            }
        });

        System.out.println("Total income: \n" + income);

        System.out.println("Total outcome: \n" + expense);
        System.out.println("Balance: \n" + balance);

        balance.forEach((category, finalBalance) -> {
            final double finalIncome = income.getOrDefault(category, 0.0);
            final double finalExpense = expense.getOrDefault(category, 0.0);

            CategoryDto categoryDto = new CategoryDto();

            categoryDto.setCategory(category);
            categoryDto.setIncome(finalIncome);
            categoryDto.setExpense(finalExpense);
            categoryDto.setBalance(finalBalance);

            categoryDtos.add(categoryDto);
        });

        return categoryDtos;
    }

    public double currentBalance() {
        double totalIncome = totalIncome();
        double totalExpense = totalExpense();

        double balance = totalIncome - totalExpense;
        return balance;
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

package service;

import entity.Transaction;
import entity.TransactionType;
import exceptions.TransactionNotFoundException;

import java.util.Comparator;
import java.util.List;

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
    }

    public void categoryWiseSummary() {
    }

    public void currentBalance() {
    }

    public void totalExpense() {
    }

    public void totalIncome() {
    }
}

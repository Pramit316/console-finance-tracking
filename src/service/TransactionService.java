package service;

import entity.Transaction;
import entity.TransactionCategory;
import entity.TransactionType;
import exceptions.InvalidAmountException;
import exceptions.TransactionNotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TransactionService {

    private List<Transaction> transactionList = new ArrayList<>();
    private int nextId = 1;

    //Uses this method to set the list by reading from the file
    public void setTransactionList(List<Transaction> transactionList){
        this.transactionList = transactionList;
    }

    //Used to initialize the nextId based on the saved data
    public void updateNextId(){
        int maxId = transactionList.stream()
                .mapToInt(Transaction::getId)
                .max()
                .orElse(0);

        nextId = maxId + 1;
    }

    //getting a snapshot of the current transaction list
    public synchronized List<Transaction> getTransactionSnapshot(){
        return new ArrayList<>(transactionList);
    }

    //Makes the next id
    public int generateId() {
        return nextId++;
    }

    public synchronized void addTransaction(Transaction transaction){
        if(transaction.getAmount() <= 0){
            throw new InvalidAmountException("Amount must be greater than zero.");
        }

        transactionList.add(transaction);
    }

    public List<Transaction> getAllTransactions(){
        return transactionList;
    }

    public Transaction findTransactionById(int id) {
        return transactionList.stream().filter(x-> x.getId() == id).findFirst().orElseThrow(() -> new TransactionNotFoundException("Transaction not found with id: " + id));
    }

    public void updateTitle(int id, String title){
        Transaction t = findTransactionById(id);
        t.setTitle(title);
    }

    public void updateAmount(int id, double amount) {
        if(amount <= 0){
            throw new InvalidAmountException("Amount must be greater than zero.");
        }
        Transaction t = findTransactionById(id);
        t.setAmount(amount);
    }

    public void updateType(int id, TransactionType transactionType) {
        Transaction t = findTransactionById(id);
        t.setType(transactionType);
    }

    public void updateDate(int id, LocalDate date) {
        Transaction transaction = findTransactionById(id);
        transaction.setDate(date);
    }

    public void updateCategory(int id, TransactionCategory selectedCategory) {
        Transaction transaction = findTransactionById(id);
        transaction.setCategory(selectedCategory);
    }

    public void updateDescription(int id, String description) {
        Transaction t = findTransactionById(id);
        t.setDescription(description);
    }

    public void updateEntireTransaction(Transaction t) {
        if(t.getAmount() <= 0){
            throw new InvalidAmountException("Amount must be greater than zero.");
        }

        Transaction tNew = findTransactionById(t.getId());

        tNew.setTitle(t.getTitle());
        tNew.setAmount(t.getAmount());
        tNew.setDescription(t.getDescription());
        tNew.setType(t.getType());
        tNew.setCategory(t.getCategory());
    }

    public List<Transaction> searchByType(TransactionType type) {
        return transactionList.stream().filter(x -> x.getType().equals(type)).toList();
    }

    public List<Transaction> searchByCategory(TransactionCategory category) {
        return transactionList.stream().filter(t -> t.getCategory().equals(category)).toList();
    }

    public List<Transaction> sortedByAmountScreen() {
        return transactionList.stream().sorted(Comparator.comparingInt(Transaction::getId)).toList().reversed();
    }

    public List<Transaction> searchByAmountRange(double lower, double upper) {
        return transactionList.stream().filter(x -> x.getAmount() >= lower && x.getAmount() <= upper ).toList();
    }

    public List<Transaction> sortedByDate() {
        return transactionList.stream().sorted(Comparator.comparing(Transaction::getDate)).toList();
    }

    public List<Transaction> searchByDate(LocalDate date) {
        return transactionList.stream().filter(x-> x.getDate().equals(date)).toList();
    }

    public void deleteTransaction(int id) {
        Transaction transaction = findTransactionById(id);
        transactionList.remove(transaction);
    }
}

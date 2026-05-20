package service;

import entity.Transaction;
import entity.TransactionType;
import exceptions.InvalidAmountException;
import exceptions.TransactionNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class TransactionService {

    private List<Transaction> transactionList = new ArrayList<>();
    private int nextId = 1;

    public int generateId() {
        return nextId++;
    }

    public void addTransaction(Transaction transaction){
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

    public void updateCategory(int id, TransactionType transactionType) {
        Transaction t = findTransactionById(id);
        t.setCategory(String.valueOf(transactionType));
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
}

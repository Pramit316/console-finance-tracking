package service;

import entity.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionService {

    private List<Transaction> transactionList = new ArrayList<>();
    private int nextId = 1;

    public int generateId() {
        return nextId++;
    }

    public void addTransaction(Transaction transaction){
        transactionList.add(transaction);
    }

    public List<Transaction> getAllTransactions(){
        return transactionList;
    }
}

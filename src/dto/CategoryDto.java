package dto;

import entity.TransactionCategory;

public class CategoryDto {
    private TransactionCategory category;
    private double income;
    private double expense;
    private double balance;

    public TransactionCategory getCategory() {
        return category;
    }

    public void setCategory(TransactionCategory category) {
        this.category = category;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setDto(TransactionCategory category, double income, double expense, double balance) {
        this.category = category;
        this.income = income;
        this.expense = expense;
        this.balance = balance;
    }
}

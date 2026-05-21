package dto;

import java.time.YearMonth;

public class MonthlySummary {
    private YearMonth month;
    private double income;
    private double expense;
    private double balance;

    public MonthlySummary(YearMonth month, double income, double expense, double balance) {
        this.month = month;
        this.income = income;
        this.expense = expense;
        this.balance = balance;
    }

    public YearMonth getMonth() {
        return month;
    }

    public void setMonth(YearMonth month) {
        this.month = month;
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
}

package cs472.mum.model;

import java.time.LocalDate;

public class Transaction {
    private int transactionId;
    private Account account;
    private LocalDate date;
    private double transactionAmount;

    public Transaction(){

    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public Account getAccount() {
        return account;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", account=" + account +
                ", date=" + date +
                ", transactionAmount=" + transactionAmount +
                '}';
    }
}

package cs472.mum.model;

import java.time.LocalDate;

public class Transaction {
    private int transactionId;
    private String accountNumber;
    private LocalDate date;
    private double transactionAmount;

    public Transaction(int transactionId, String accountNumber, double transactionAmount ){
        this.transactionId= transactionId;
        this.accountNumber= accountNumber;
        this.date= LocalDate.now();
        this.transactionAmount= transactionAmount;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public void setAccount(String accountNumber) {
        this.accountNumber = accountNumber;
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

    public String getAccountNumber() {
        return accountNumber;
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
                ", account=" + accountNumber +
                ", date=" + date +
                ", transactionAmount=" + transactionAmount +
                '}';
    }
}

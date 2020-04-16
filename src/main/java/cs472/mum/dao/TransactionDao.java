package cs472.mum.dao;

import cs472.mum.model.Account;
import cs472.mum.model.Transaction;

import java.time.LocalDate;
import java.util.*;

public class TransactionDao {
    private HashMap<Integer, Transaction>  transDb;
    private Transaction transaction;

    public TransactionDao() {
        transDb = new HashMap<>();
    }
    public Transaction createTransaction( int transactionId, String accountNumber, double transactionAmount) {
        transaction = new Transaction(transactionId,accountNumber, transactionAmount);
        return transaction;
    }

    public void deleteTransaction(int transactionId) {
        transDb.remove(transactionId);
    }


    public Transaction readTransaction(int transactionId) {
        Transaction transaction = transDb.get(transactionId);
        return transaction;
    }

    public HashMap<Integer, Transaction> readAllTransaction() {
        return transDb;
    }
    public void addTransaction(int transactionID, Transaction transaction){
        transDb.put(transactionID, transaction);
    }

}
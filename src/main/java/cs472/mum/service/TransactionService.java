package cs472.mum.service;

import cs472.mum.dao.TransactionDao;
import cs472.mum.model.Account;
import cs472.mum.model.Transaction;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionService {
    private TransactionDao transactionDao;

    public TransactionService(){
        transactionDao= new TransactionDao();
    }
    //creates transaction and adds it to memory
    public boolean createTransaction( int transactionId, String accountNumber, double transactionAmount) {
        if(!transactionDao.readAllTransaction().containsKey(accountNumber)) {
            Transaction transaction=transactionDao.createTransaction(transactionId,accountNumber,transactionAmount);
            transactionDao.addTransaction(transaction.getTransactionId(),transaction);
            return true;
        }
        else
            return false;
    }

    //Deletes transaction from Memory
    /*
    public boolean deleteTransaction(int transactionId) {
        if (transactionDao.readAllTransaction().containsKey(transactionId)) {
            transactionDao.deleteTransaction(transactionId);
            return true;
        } else
            return false;
    }
*/
    //reads transaction based on transaction ID
    /*
    public Transaction readTransaction(int transactionId) {
        Transaction transaction;
        if (transactionDao.readAllTransaction().containsKey(transactionId)) {
            transaction = transactionDao.readTransaction(transactionId);
            return transaction;
        } else
            return null;
    }
*/
    //read all transactions based on the account number
    public List<Transaction> readAllTransaction(String accountNumber) {
        List<Transaction> transactions = new ArrayList<>();
        String tempAcc;
        for (Map.Entry<Integer, Transaction> entry : transactionDao.readAllTransaction().entrySet()){
            tempAcc = entry.getValue().getAccountNumber();
            if (accountNumber.equals(tempAcc)) {
                transactions.add(entry.getValue());
            }
        }
        if (!transactions.isEmpty()) {
            return transactions;
        } else
            return null;
    }


    //returns transaction based on date
    public List<Transaction> transactionsFromDate(String date, String accountNumber){
        List <Transaction> allTransactions= readAllTransaction(accountNumber);
        List<Transaction> filterdTransactions= new ArrayList<>();
        LocalDate newDate= LocalDate.parse(date);
        for(Transaction transaction: allTransactions){
            if(transaction.getDate().compareTo(newDate)<=0){
                filterdTransactions.add(transaction);
            }
        }
        return filterdTransactions;
    }


    //checks if transaction is available based on transaction ID
    /*
    public Boolean isAvailable(int transactionId){
        if(!transactionDao.readAllTransaction().isEmpty()) {
            return true;
        }
        else
            return false;
    }
    */
    //returns all transactions- in memory
    public HashMap<Integer, Transaction> getAllTransactions(){
        return transactionDao.readAllTransaction();
    }

}
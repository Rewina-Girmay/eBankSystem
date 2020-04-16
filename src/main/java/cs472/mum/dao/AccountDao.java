package cs472.mum.dao;

import cs472.mum.model.Account;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class AccountDao implements Serializable {
    private HashMap<String , Account> accounts;

    public AccountDao() {

        accounts = new HashMap<>();
        Account account1 = new Account(2000, "Saving", "12345");
        Account account2 = new Account(2000, "Saving", "12346");
        Account account3 = new Account(2000, "Saving", "12347");

        accounts.put(account1.getAccountNumber(), account1);
        accounts.put(account2.getAccountNumber(), account2);
        accounts.put(account3.getAccountNumber(), account3);


    }

    public AccountDao(HashMap<String, Account> accounts) {
        this.accounts = accounts;
    }

    // Add account to collection
    public void addAccount(Account account){
        accounts.put(account.getAccountNumber(),account);
    }

    // Get account using accNo
    public Account getAccount(String accNo) {
        System.out.println("I was here ");


        if (accounts.get(accNo) == null) {
            System.out.println("false");
        }
        else {
            System.out.println("True");
        }
        return accounts.get(accNo);
    }


    // Get All Accounts from HashMap
    public HashMap<String, Account> getAllAccounts() {
        return accounts;
    }


    // Create account
    public boolean createAccount(Account account) {
        if (accounts.containsKey(account.getAccountNumber())) {
            return false;
        }
        else {
            accounts.put(account.getAccountNumber(), account);
            return true;
        }

    }

    // update Account

    public boolean updateAccount(Account account) {
        accounts.put(account.getAccountNumber(), account);
        return true;
    }




}

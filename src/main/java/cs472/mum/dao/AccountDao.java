package cs472.mum.dao;

import cs472.mum.model.Account;

import java.util.HashMap;

public class AccountDao {
    private HashMap<String , Account> accounts;

    public AccountDao() {
    }

    public AccountDao(HashMap<String, Account> accounts) {
        this.accounts = accounts;
    }

    public void addAccount(Account account){
        accounts.put(account.getAccountNumber(),account);
    }

}

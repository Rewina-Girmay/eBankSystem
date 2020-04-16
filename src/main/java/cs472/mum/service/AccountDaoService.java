package cs472.mum.service;

import cs472.mum.dao.AccountDao;
import cs472.mum.model.Account;
import cs472.mum.model.Transaction;

import java.io.Serializable;
import java.util.HashMap;

public class AccountDaoService implements Serializable {

    private AccountDao accountDao;

    public AccountDaoService() {
        accountDao = new AccountDao();
    }

    // Pay money
    public String payMoney(String sender, String accepter, double amount) {
        Account payer  =  getAccount(sender);
        Account receiver = getAccount(accepter);
        if (payer == null || receiver == null) {
            return "{'error': 'The account number is invalid' }";
        }

        else {

            double currentBalance = payer.getBalance();

            if(currentBalance >= amount) {
                payer.setBalance(currentBalance-amount);
                accountDao.addAccount(payer);
                double receiverbalance = receiver.getBalance();
                receiver.setBalance(receiverbalance + currentBalance);

                accountDao.addAccount(receiver);

                return "{'success': " + payer.getBalance() + "}";



            }
            else {
                return "{'error': 'You donot have enough money'}";
            }
        }




    }


    // Create New Account
    public boolean createAccount(Account account) {
        return accountDao.createAccount(account);
    }

    // Get all accounts
    public HashMap<String, Account> getAccounts() {
        return accountDao.getAllAccounts();
    }

    // Get account based on accNo
    public Account getAccount(String accNo) {

        return accountDao.getAccount(accNo);
    }

    public boolean updateAccount(Account account) {
        return accountDao.updateAccount(account);
    }

}

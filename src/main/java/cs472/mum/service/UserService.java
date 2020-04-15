package cs472.mum.service;

import cs472.mum.dao.UserDao;
import cs472.mum.model.Account;
import cs472.mum.model.User;

import java.util.List;

public class UserService {

    private UserDao userDao;

    public UserService(){
        userDao = new UserDao();
    }

    //getting the account number
    public String accountNumber(String username){
        return userDao.getAccountNUmber(username);
    }

    //add user and password
    public boolean addUser(User user){
        if(!userDao.getUsers().containsKey(user.getUserName())){
            userDao.addUser(user);
            return true;
        }
        else{
            return false;
        }
    }
    //validate the username and password
    public String validateUser(String username, String password) {
        User user = userDao.getUserByUsername(username);
        String account = null;
        if (user != null) {
            if(user.getPassword().equals(password)){
                account = user.getAccountNumber();
            }
        }
        return account;
    }

    public String getAll(){
        return  userDao.getUsers().toString();
    }
}


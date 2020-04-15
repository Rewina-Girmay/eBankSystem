package cs472.mum.dao;


import cs472.mum.model.Account;
import cs472.mum.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class UserDao {

    // private User user;
    private HashMap<String, User> repo = new HashMap<>();

    public UserDao(){
        repo.put("rewi",new User("rewi","12435","rewi"));
        repo.put("lili",new User("lili","67890","lili"));
    }
    //get account number for rendering page
    public String getAccountNUmber(String username) {
        return getUserByUsername(username).getAccountNumber();
    }
    //add new user;
    public void addUser(User user) {
        repo.put(user.getUserName(),user);

    }

    //get user by username
    public User getUserByUsername(String username){
        return repo.get(username);
    }

    //delete user
    public void deleteUser(String username){
        repo.remove(username);
    }

    public HashMap<String,User> getUsers(){
        return repo;
    }
}
package cs472.mum.controller;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import cs472.mum.dao.AccountDao;
import cs472.mum.model.Account;
import cs472.mum.model.User;
import cs472.mum.service.AccountDaoService;
import cs472.mum.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class UserController extends HttpServlet {

    private UserService service;
    private AccountDaoService accountDaoService;
    private Gson gson;

    public void init() {
        gson = new Gson();
        service = new UserService();
        accountDaoService = new AccountDaoService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String pathInfo = request.getPathInfo();
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String[] path = pathInfo.split("/");
        String payload = buffer.toString();
        if (pathInfo == null || pathInfo.equals("/")) {

            User user = gson.fromJson(payload, User.class);

            if (service.addUser(user)) {
                sendAsJson(response, user);
            } else {
                String error = "{message: user already exists}";
                gson.toJson(error);
                sendAsJson(response, error);
            }
        }


        if (path[1].equals("login") && path.length == 2) {
            System.out.println("payload" + payload);
            JsonObject jobj = gson.fromJson(payload, JsonObject.class);
            String username = jobj.get("userName").getAsString();
            String password = jobj.get("password").getAsString();
        System.out.println(username + " " + password);
            String accountNumber = service.validateUser(username, password);
            if (accountNumber != null) {
                Account account = accountDaoService.getAccount(accountNumber);

                HttpSession session = request.getSession();
                session.setAttribute("account", account);
                Cookie cookie = new Cookie("login", "yes");
                response.addCookie(cookie);
                System.out.println("account Info: " + account.toString());
                sendAsJson(response, account.getAccountNumber());
                return;
            } else {
                String error = "{error username or password}";
                gson.toJson(error);
                sendAsJson(response, error);
                return;
            }

        }

        if (path[1].equals("logout") && path.length == 2) {
            HttpSession session = request.getSession();
            session.invalidate();
            String error = "{you have logout successfully!}";
            gson.toJson(error);
            sendAsJson(response, error);
            return;
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    private void sendAsJson(HttpServletResponse response, Object obj) throws IOException {

        fixHeaders(response);
        String res = gson.toJson(obj);
        PrintWriter out = response.getWriter();
        out.print(res);
        out.flush();
    }

    private void fixHeaders(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "*");
        response.addHeader("Access-Control-Allow-Headers", "*");
        response.addHeader("Access-Control-Max-Age", "86400");
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doOptions(req, resp);
        fixHeaders(resp);
    }


}

package cs472.mum.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import cs472.mum.model.Account;
import cs472.mum.service.AccountDaoService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

public class AccountServlet  extends HttpServlet {

    private AccountDaoService accountDaoService;
    private Gson gson = null;

    public void init() {
        gson = new Gson();
        accountDaoService = new AccountDaoService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pathInfo = req.getPathInfo();
        System.out.println("Path information " + pathInfo);
        if(pathInfo == null || pathInfo.equals("/")){

            Collection<Account> accounts = accountDaoService.getAccounts().values();

            sendAsJson(resp, accounts);
            return;
        }

        String[] splits = pathInfo.split("/");

        if(splits.length != 2) {

            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String accNo = splits[1];

        if(!accountDaoService.getAccounts().containsKey(accNo)) {

            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        sendAsJson(resp, accountDaoService.getAccounts().get(accNo));
        return;


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pathInfo = req.getPathInfo();
        String [] path = pathInfo.split("/");

        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }

        String payload = buffer.toString();

        if(pathInfo == null || pathInfo.equals("/")){

            Account account = gson.fromJson(payload, Account.class);
            accountDaoService.createAccount(account);
            sendAsJson(resp, account);
        }

        else if (path[1].equals("pay") && path.length == 2) {

            if (req.getSession(false) !=null) {
                JsonObject jobj = gson.fromJson(payload, JsonObject.class);
//                String payer = jobj.get("payer").getAsString();

                String reciver = jobj.get("rcv").getAsString();
                double amount = jobj.get("amount").getAsDouble();
                Account account = (Account) req.getSession().getAttribute("account");
                String confirmation =  accountDaoService.payMoney(account.getAccountNumber(), reciver, amount);

                sendAsJson(resp, confirmation);
            }
            else {
                String message =  "{error : you must login first }";
                sendAsJson(resp, message);
            }


        }
        else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    //a utility method to send object
    //as JSON response
    private void sendAsJson(HttpServletResponse response, Object obj) throws IOException {

        fixHeaders(response);

        String res = gson.toJson(obj);

        PrintWriter out = response.getWriter();

        out.print(res);
        out.flush();
    }

//    // Send Json Directly
//    private  void sendJson(HttpServletResponse response, JsonObject jsonObject) throws IOException{
//        fixHeaders(response);
//        PrintWriter out = response.getWriter();
//
//        out.print(jsonObject);
//        out.flush();
//    }


    private void fixHeaders(HttpServletResponse response) {

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "*");
        response.addHeader("Access-Control-Allow-Headers", "*");
        response.addHeader("Access-Control-Max-Age", "86400");
        response.setContentType("application/json");
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doOptions(req, resp);
        setAccessControlHeaders(resp);
    }

    private void setAccessControlHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5500");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
    }
}

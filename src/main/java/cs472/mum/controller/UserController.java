package cs472.mum.controller;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import cs472.mum.model.User;
import cs472.mum.service.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class UserController extends HttpServlet {

    private UserService service;
    private Gson gson;

    public void init() {
        gson = new Gson();
        service = new UserService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String pathInfo = request.getPathInfo();
        StringBuilder buffer = new StringBuilder();
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
                HttpSession session = request.getSession();
                sendAsJson(response, user);
            } else {
                String error = "{message: user already excist}";
                gson.toJson(error);
                sendAsJson(response, error);
            }
        }


        if (path[1].equals("login") && path.length == 2) {
            JsonObject jobj = gson.fromJson(payload, JsonObject.class);
            String username = jobj.get("userName").getAsString();
            String password = jobj.get("password").getAsString();
            System.out.println(username + " " + password);
            String accountNumber = service.validateUser(username, password);
            if (accountNumber != null) {
                HttpSession session = request.getSession();
                sendAsJson(response, accountNumber);
            } else {
                String error = "{error username or password}";
                gson.toJson(error);
                sendAsJson(response, error);
            }

        }

        if (path[1].equals("logout") && path.length == 2) {
            HttpSession session = request.getSession();
            session.invalidate();
            String error = "{you have logout successfully!}";
            gson.toJson(error);
            sendAsJson(response, error);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    private void sendAsJson(HttpServletResponse response, Object obj) throws IOException {
        fixHeaders(response);
        response.setContentType("application/json");
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
        response.setContentType("application/json");
    }
}

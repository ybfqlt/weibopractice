package cc.openhome.controller;

import cc.openhome.model.UserService;

import java.io.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        urlPatterns={"/register.do"},
        initParams= {@WebInitParam(name = "SUCCESS_VIEW", value = "success.view"),
                @WebInitParam(name = "ERROR_VIEW", value = "error.view")
        }
)
public class Register extends HttpServlet {
    private String SUCCESS_VIEW;
    private String ERROR_VIEW;
    @Override
    public void init() throws ServletException{
        SUCCESS_VIEW=getServletConfig().getInitParameter("SUCCESS_VIEW");
        ERROR_VIEW=getServletConfig().getInitParameter("ERROR_VIEW");
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmedPasswd = request.getParameter("confirmedPasswd");
        UserService userService = (UserService)getServletContext().getAttribute("userService");
        List<String> errors = new ArrayList<String>();
        if(isInvalidEmail(email)) {
            errors.add("未填写邮件或邮件格式不正确");
        }
        if(userService.isInvalidUsername(username)) {
            errors.add("用户名称为空或已存在");
        }
        if(isInvalidPassword(password,confirmedPasswd)) {
            errors.add("请确认密码符合格式并再次确认密码");
        }
        String resultPage = ERROR_VIEW;
        if(!errors.isEmpty()) {
            request.setAttribute("errors", errors);
        }
        else {
            resultPage = SUCCESS_VIEW;
            userService.createUserData(email,username,password);
        }
        request.getRequestDispatcher(resultPage).forward(request,response);
    }
    private boolean isInvalidEmail(String email) {
        return email == null || !email.matches("^[_a-z0-9-]+([.]" + "[_a-z0-9-]+)*@[a-z0-9-]+([.][a-z0-9-]+)*$");
    }
    private boolean isInvalidPassword(String password,String confirmedPasswd) {
        return password == null || password.length() < 6 || password.length() > 16 || !password.equals(confirmedPasswd);
    }
}

/*
import java.io.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register.do")
public class Register extends HttpServlet {
    private final String USERS = "/home/ltt/IdeaProjects/Gossip/users";
    private final String SUCCESS_VIEW = "success.view";
    private final String ERROR_VIEW = "error.view";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmedPasswd = request.getParameter("confirmedPasswd");
        List<String> errors = new ArrayList<String>();
        if(isInvalidEmail(email)) {
            errors.add("未填写邮件或邮件格式不正确");
        }
        if(isInvalidUsername(username)) {
            errors.add("用户名称为空或已存在");
        }
        if(isInvalidPassword(password,confirmedPasswd)) {
            errors.add("请确认密码符合格式并再次确认密码");
        }
        String resultPage = ERROR_VIEW;
        if(!errors.isEmpty()) {
            request.setAttribute("errors", errors);
        }
        else {
            resultPage = SUCCESS_VIEW;
            createUserData(email, username, password);
        }
        request.getRequestDispatcher(resultPage).forward(request,response);
    }
    private boolean isInvalidEmail(String email) {
        return email == null || !email.matches("^[_a-z0-9-]+([.]" + "[_a-z0-9-]+)*@[a-z0-9-]+([.][a-z0-9-]+)*$");
    }
    private boolean isInvalidUsername(String username) {
        for (String file : new File(USERS).list()) {
            if (file.equals(username)) {
                return true;
            }
        }
        return false;
    }
    private boolean isInvalidPassword(String password,String confirmedPasswd) {
        return password == null || password.length() < 6 || password.length() > 16 || !password.equals(confirmedPasswd);
    }
    private void createUserData(String email,String username,String password) throws IOException {
        File userhome = new File(USERS + "/" + username);
        userhome.mkdir();
        BufferedWriter writer = new BufferedWriter(new FileWriter(userhome + "/profile"));
        writer.write(email + "\t" + password);
        writer.close();
    }
}
*/

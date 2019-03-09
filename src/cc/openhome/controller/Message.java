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
        urlPatterns={"/message.do"},
        initParams= {
                @WebInitParam(name = "SUCCESS_VIEW", value = "member.view"),
                @WebInitParam(name = "ERROR_VIEW", value = "member.view")
        }
)
public class Message extends HttpServlet {
    private String SUCCESS_VIEW;
    private String ERROR_VIEW;
    @Override
    public void init() throws ServletException{
        SUCCESS_VIEW=getServletConfig().getInitParameter("SUCCESS_VIEW");
        ERROR_VIEW=getServletConfig().getInitParameter("ERROR_VIEW");
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String blabla = request.getParameter("blabla");
        if (blabla != null && blabla.length() != 0) {
            if (blabla.length() < 140) {
                String username = (String) request.getSession().getAttribute("login");
                UserService userService = (UserService) getServletContext().getAttribute("userService");
                userService.addMessage(username, blabla);
                response.sendRedirect(SUCCESS_VIEW);
            } else {
                request.getRequestDispatcher(ERROR_VIEW).forward(request, response);
            }
        } else {
            response.sendRedirect(ERROR_VIEW);
        }
    }
}


/*
@WebServlet("/message.do")
public class Message extends HttpServlet {
    private final String USERS="/home/ltt/IdeaProjects/Gossip/users";
    private final String LOGIN_VIEW="index.html";
    private final String SUCCESS_VIEW="member.view";
    private final String ERROR_VIEW = "member.view";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("login")==null){
            response.sendRedirect(LOGIN_VIEW);
            return;
        }
        request.setCharacterEncoding("UTF-8");
        String blabla = request.getParameter("blabla");
        if(blabla != null && blabla.length() != 0){
            if(blabla.length()<140){
                String username=(String)request.getSession().getAttribute("login");
                addMessage(username,blabla);
                response.sendRedirect(SUCCESS_VIEW);
            }
            else{
                request.getRequestDispatcher(ERROR_VIEW).forward(request,response);
            }
        }
        else{
            response.sendRedirect(ERROR_VIEW);
        }
    }
    private void addMessage(String username,String blabla) throws IOException{
        String file = USERS+"/"+username+"/"+new Date().getTime()+".txt";
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
        writer.write(blabla);
        writer.close();
    }
}
*/

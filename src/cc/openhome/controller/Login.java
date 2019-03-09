package cc.openhome.controller;

import cc.openhome.model.UserService;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;




@WebServlet(
        urlPatterns={"/login.do"},
        initParams={
                @WebInitParam(name="SUCCESS_VIEW",value="member.view"),
                @WebInitParam(name="ERROR_VIEW",value="index.html")
        }
)
public class Login extends HttpServlet {
    private String SUCCESS_VIEW;
    private String ERROR_VIEW;
    @Override
    public void init() throws ServletException{
        SUCCESS_VIEW = getServletConfig().getInitParameter("SUCCESS_VIEW");
        ERROR_VIEW=getServletConfig().getInitParameter("ERROR_VIEW");
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String page = ERROR_VIEW;
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        if(userService.checkLogin(username,password)){
            request.getSession().setAttribute("login",username);
            page=SUCCESS_VIEW;
        }
        response.sendRedirect(page);
    }
}






/*
@WebServlet("/login.do")
public class Login extends HttpServlet {
    private final String USERS = "/home/ltt/IdeaProjects/Gossip/users";
    private final String SUCCESS_VIEW = "member.view";
    private final String ERROR_VIEW="index.html";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
 if(checkLogin(username,password)){
            request.getRequestDispatcher(SUCCESS_VIEW).forward(request,response);
        }
        else{
            response.sendRedirect(ERROR_VIEW);
        }

       String page = ERROR_VIEW;
       if(checkLogin(username,password)){
           request.getSession().setAttribute("login",username);
           page=SUCCESS_VIEW;
       }
       response.sendRedirect(page);
    }
    private boolean checkLogin(String username,String password)throws IOException{
        if(username!=null&&password!=null){
            for(String file:new File(USERS).list()){
                if(file.equals(username)){
                    BufferedReader reader = new BufferedReader(new FileReader(USERS+"/"+file+"/profile"));
                    String passwd = reader.readLine().split("\t")[1];
                    if(passwd.equals(password)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
*/


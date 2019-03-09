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
        urlPatterns = {"/delete.do"},
        initParams={
                @WebInitParam(name="SUCCESS_VIEW",value="member.view")
        }
)
public class Delete extends HttpServlet {
    private String SUCCESS_VIEW;
    @Override
    public void init() throws ServletException{
        SUCCESS_VIEW=getServletConfig().getInitParameter("SUCCESS_VIEW");
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username=(String)request.getSession().getAttribute("login");
        String message = request.getParameter("message");
        UserService userService = (UserService)getServletContext().getAttribute("userService");
        userService.deleteMessage(username,message);
        response.sendRedirect(SUCCESS_VIEW);
    }
}
/*@WebServlet("/delete.do")
public class Delete extends HttpServlet {
    private final String USERS = "/home/ltt/IdeaProjects/Gossip/users";
    private final String LOGIN_VIEW="index.html";
    private final String SUCCESS_VIEW="member.view";
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("login")==null){
            response.sendRedirect(LOGIN_VIEW);
            return;
        }
        String username=(String)request.getSession().getAttribute("login");
        String message = request.getParameter("message");
        File file = new File(USERS+"/"+username+"/"+message+".txt");
        if(file.exists()){
            file.delete();
        }
        response.sendRedirect(SUCCESS_VIEW);
    }
}*/

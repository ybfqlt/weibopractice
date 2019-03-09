package cc.openhome.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@WebServlet(
        urlPatterns={"/logout.do"},
        initParams={
                @WebInitParam(name="LOGIN_VIEW",value="index.html")
        }
)
public class Logout extends HttpServlet {
    private String LOGIN_VIEW;

    @Override
    public void init() throws ServletException{
        LOGIN_VIEW=getServletConfig().getInitParameter("LOGIN_VIEW");
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect(LOGIN_VIEW);
    }
}




/*
@WebServlet("/logout.do")
public class Logout extends HttpServlet {
    private final String LOGIN_VIEW = "index.html";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("login")!=null){
            request.getSession().invalidate();
        }
        response.sendRedirect(LOGIN_VIEW);
    }
}
*/

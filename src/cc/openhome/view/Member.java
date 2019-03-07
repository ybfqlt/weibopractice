package cc.openhome.view;

import java.io.*;
import java.util.*;
import java.util.Map;
import java.text.DateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/member.view")
public class Member extends HttpServlet {
    private final String LOGIN_VIEW = "index.html";
    private final String USERS = "/home/ltt/IdeaProjects/Gossip/users";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       if(request.getSession().getAttribute("login")==null) {
           response.sendRedirect(LOGIN_VIEW);
           return;
       }
       String username = (String)request.getSession().getAttribute("login");
       response.setContentType("text/html;charset=UTF-8");
       PrintWriter out = response.getWriter();
       out.println("<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'>");
       out.println("<html>");
       out.println("<head>");
       out.println("  <meta content='text/html; charset=UTF-8' http-equiv='content-type'>");
       out.println("  <title>会员登入成功</title>");
       out.println("</head>");
       out.println("<body>");
       //out.println("<h1>会员 " + username + " 你好</h1>");
       out.println("</body>");
       out.println("<img src='caterpillar.png'　alt='Gossip 微博'/>");
       out.println("<br><br>");
       out.println("<a href='logout.do?username=" + username + "'>登出 " + username + "</a>");//之前登出显示不出来，原因为上面微博后的小符号，用的中文符号，导致这一句无效
       out.println("</div>");
       out.println("<form method='post' action='message.do'>");
       out.println("分享新鲜事...<br>");
       String blabla = request.getParameter("blabla");
       if(blabla == null){
           blabla="";
       }
       else{
           out.println("信息要140字以内<br>");
       }
       out.println("<textarea cols='60' rows='4' name='blabla'>"+blabla+"</textarea>");
       out.println("<br>");
       out.println("<button type = 'submit'>送出</button>");
       out.println("</form>");
       out.println("<table style ='text-align: left;width: 510px;height:88px;'border='0' cellpadding='2' cellspacing='2'>");
       out.println("<thead>");
       out.println("<tr><th><hr></th></tr>");
       out.println("/thead>");
       out.println("<tbody>");
       Map<Date, String> messages = readMessage(username);
       DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.FULL,Locale.TAIWAN);
       for(Date date: messages.keySet()){
           out.println("<tr><td style='vertical-align: top;'>");
           out.println(username+"<br>");
           out.println(messages.get(date)+"<br>");
           out.println(dateFormat.format(date));
           out.println("<a href='delete.do?message=" + date.getTime() + "'>删除</a>");
           out.println("<hr></td></tr>");
       }
       out.println("</html>");
       out.close();
    }
    //用以过滤.txt文件名
    private class TxtFilenameFilter implements FilenameFilter{
        @Override
        public boolean accept(File dir,String name){
            return name.endsWith(".txt");
        }
    }
    private TxtFilenameFilter filenameFilter = new TxtFilenameFilter();
    private class DateComparator implements Comparator<Date>{
        @Override
        public int compare(Date d1,Date d2){
            return -d1.compareTo(d2);
        }
    }
    private DateComparator comparator = new DateComparator();

    private Map<Date,String> readMessage(String username) throws IOException{
        File border = new File(USERS+"/"+username);
        String[] txts = border.list(filenameFilter);
        Map<Date,String> messages = new TreeMap<Date,String>(comparator);
        for(String txt:txts){
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(USERS+"/"+username+"/"+txt),"UTF-8"));
            String text=null;
            StringBuilder builder = new StringBuilder();
            while((text = reader.readLine())!=null){
                builder.append(text);
            }
            Date date = new Date(Long.parseLong(txt.substring(0,txt.indexOf(".txt"))));
            messages.put(date,builder.toString());
            reader.close();
        }
        return messages;
    }

    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
        processRequest(request,response);
    }
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        processRequest(request,response);
    }
}
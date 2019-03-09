package cc.openhome.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import java.io.IOException;


public class UserService {
    private String USERS;

    public UserService(String USERS) {
        this.USERS = USERS;
    }

    //检查是否为不合法用户名称
    public boolean isInvalidUsername(String username) {
        for (String file : new File(USERS).list()) {
            if (file.equals(username)) {
                return true;
            }
        }
        return false;
    }

    //创建用户目录与基本资料
    public void createUserData(String email, String username, String password) throws IOException {
        File userhome = new File(USERS + "/" + username);
        userhome.mkdir();
        BufferedWriter writer = new BufferedWriter(new FileWriter(userhome + "/profile"));
        writer.write(email + "\t" + password);
        writer.close();
    }

    //检查登录用户名称与密码
    public boolean checkLogin(String username, String password) throws IOException {
        if (username != null && password != null) {
            for (String file : new File(USERS).list()) {
                if (file.equals(username)) {
                    BufferedReader reader = new BufferedReader(new FileReader(USERS + "/" + file + "/profile"));
                    String passwd = reader.readLine().split("\t")[1];
                    if (passwd.equals(password)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private class TxtFilenameFilter implements FilenameFilter {
        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(".txt");
        }
    }

    private TxtFilenameFilter filenameFilter = new TxtFilenameFilter();

    private class DateComparator implements Comparator<Date> {
        @Override
        public int compare(Date d1, Date d2) {
            return -d1.compareTo(d2);
        }
    }

    private DateComparator comparator = new DateComparator();

    //读取用户的信息
    public Map<Date, String> readMessage(String username) throws IOException {
        File border = new File(USERS + "/" + username);
        String[] txts = border.list(filenameFilter);
        Map<Date, String> messages = new TreeMap<Date, String>(comparator);
        for (String txt : txts) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(USERS + "/" + username + "/" + txt), "UTF-8"));
            String text = null;
            StringBuilder builder = new StringBuilder();
            while ((text = reader.readLine()) != null) {
                builder.append(text);
            }
            Date date = new Date(Long.parseLong(txt.substring(0, txt.indexOf(".txt"))));
            messages.put(date, builder.toString());
            reader.close();
        }
        return messages;
    }

    //新增消息
    public void addMessage(String username, String blabla) throws IOException {
        String file = USERS + "/" + username + "/" + new Date().getTime() + ".txt";
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
        writer.write(blabla);
        writer.close();
    }

    public void deleteMessage(String username, String message) {
        File file = new File(USERS + "/" + username + "/" + message + ".txt");
        if (file.exists()) {
            file.delete();
        }
    }
}

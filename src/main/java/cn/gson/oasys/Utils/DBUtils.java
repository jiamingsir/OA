package cn.gson.oasys.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;

public class DBUtils {


    private static String driver;
    private static String url;
    private static String user;
    private static String password;
    private static String initSize;
    private static String max;
    private static BasicDataSource ds;


    static {
        try {

            //4、从prop中获取数据库连接参数
            driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            url = "jdbc:sqlserver://192.168.0.245:1433;DatabaseName=a1b501";
            //url = "jdbc:sqlserver://localhost:1433;DatabaseName=2014XYPTMMtest2";
            user = "sa";
            password = "Xinyun2304";
            ds = new BasicDataSource();
            /*b、设置数据库连接参数*/
            ds.setDriverClassName(driver);
            ds.setUrl(url);
            ds.setUsername(user);
            ds.setPassword(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**封装数据库连接池
     * @throws Exception */
    public static Connection getConn() throws Exception{
        //d、获取连接对�?
        Connection conn = ds.getConnection(); //从连接池�?
        return conn;
    }

    /**扑捉异常*/
    public static void closeConn(Connection conn) {
        if(conn!=null) {
            try {
                conn.setAutoCommit(true);//�?启事�?
                /**此时因为使用了数据库连接池，close不再是关闭�?�是归还到连接池*/
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**回滚异常*/
    public static void rollback(Connection conn) {
        if(conn!=null) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

    }






}




























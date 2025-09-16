package util;


import java.sql.Connection;
import java.sql.DriverManager;


public class DBUtil {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=CharityPlatform;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "tYang0629";

    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("無法連接資料庫" + e.getMessage(), e);
        }
    }
	
//	private static DataSource dataSource;
//	
//	static {
//		try {
//            // 取得 JNDI context
//            Context initContext = new InitialContext();
//            Context envContext = (Context) initContext.lookup("java:comp/env");
//            
//            // 取得資料來源（JNDI 名稱需與 context.xml 一致）
//            dataSource = (DataSource) envContext.lookup("jdbc/CharityDB");
//
//        } catch (NamingException e) {
//            e.printStackTrace();
//            throw new RuntimeException("找不到資料來源 jdbc/CharityDB，請確認 context.xml 是否正確設定");
//        }
//    }
//
//    public static Connection getConnection() throws SQLException {
//        return dataSource.getConnection();
//    }
	
}

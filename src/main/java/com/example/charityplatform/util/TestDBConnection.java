package util;

import java.sql.Connection;

public class TestDBConnection {
    public static void main(String[] args) {
        try (Connection conn = DBUtil.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("資料庫連線成功！");
            } else {
                System.out.println("資料庫連線失敗！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package util;


import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import java.sql.*;

@WebListener
public class DatabaseSetupListener implements ServletContextListener {

    private final String DB_URL_MASTER = "jdbc:sqlserver://localhost:1433;databaseName=master;encrypt=true;trustServerCertificate=true";
    private final String DB_URL_CHARITY = "jdbc:sqlserver://localhost:1433;databaseName=CharityPlatform;encrypt=true;trustServerCertificate=true";
    private final String USER = "sa";
    private final String PASS = "tYang0629";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try (Connection conn = DriverManager.getConnection(DB_URL_MASTER, USER, PASS);
             Statement stmt = conn.createStatement()) {

            String sqlCreateDB =
                    "IF DB_ID(N'CharityPlatform') IS NULL\n" +
                    "BEGIN\n" +
                    "    CREATE DATABASE CharityPlatform\n" +
                    "END";
            stmt.executeUpdate(sqlCreateDB);

            // 建立資料表
            try (Connection dbConn = DriverManager.getConnection(DB_URL_CHARITY, USER, PASS);
                 Statement dbStmt = dbConn.createStatement()) {

                String createUserTable =
                        "IF OBJECT_ID('Users', 'U') IS NULL\n" +
                        "BEGIN\n" +
                        "    CREATE TABLE Users (\n" +
                        "        UserID INT IDENTITY(1,1) PRIMARY KEY,\n" +
                        "        UserAccount VARCHAR(50) NOT NULL UNIQUE,\n" +
                        "        Password VARCHAR(255) NOT NULL,\n" +
                        "        FullName NVARCHAR(50) NOT NULL,\n" +
                        "        Email VARCHAR(100) NOT NULL,\n" +
                        "        Phone VARCHAR(20) NULL,\n" +
                        "        Address NVARCHAR(200) NULL,\n" +
                        "        Birthday DATE NULL,\n" +
                        "        Gender CHAR(1) NULL,\n" +
                        "        CreateTime DATETIME DEFAULT GETDATE()\n" +
                        "    )\n" +
                        "END";
                dbStmt.executeUpdate(createUserTable);

                String createCharitiesTable =
                        "IF OBJECT_ID('Charities', 'U') IS NULL\n" +
                        "BEGIN\n" +
                        "    CREATE TABLE Charities (\n" +
                        "        CharitiesID INT IDENTITY(1,1) PRIMARY KEY,\n" +
                        "        CharitiesAccount VARCHAR(50) NOT NULL UNIQUE,\n" +
                        "        CharitiesPassword VARCHAR(255) NOT NULL,\n" +
                        "        CharitiesAddress NVARCHAR(200) NULL,\n" +
                        "        CharitiesPhone VARCHAR(20) NOT NULL,\n" +
                        "        CharitiesNo VARCHAR(50) NOT NULL,\n" +
                        "        CharitiesFile NVARCHAR(250) NOT NULL\n" +
                        "    )\n" +
                        "END";
                dbStmt.executeUpdate(createCharitiesTable);
            }

            System.out.println("資料庫及資料表檢查並建立完成");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // 通常不需要清理資源
    }
}

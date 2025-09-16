package util;

import java.sql.Connection;
import java.sql.Statement;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyDBInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("WebApp 啟動，開始建立資料表...");
        try (Connection conn = DBUtil.getConnection(); Statement stmt = conn.createStatement()) {

            String userTableSql = "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Users' and xtype='U') " +
                    "CREATE TABLE Users (" +
                    "UserID INT IDENTITY(1,1) PRIMARY KEY, " +
                    "UserAccount VARCHAR(20) NOT NULL UNIQUE, " +
                    "Password VARCHAR(20) NOT NULL, " +
                    "FullName NVARCHAR(10) NOT NULL, " +
                    "Email VARCHAR(50) NOT NULL, " +
                    "Phone VARCHAR(10), " +
                    "Address NVARCHAR(100), " +
                    "Birthday DATE, " +
                    "Gender CHAR(1), " +
                    "CreateTime DATETIME DEFAULT GETDATE()" +
                    ")";

            String charityTableSql = "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Charities' and xtype='U') " +
                    "CREATE TABLE Charities (" +
                    "CharitiesID INT IDENTITY(1,1) PRIMARY KEY, " +
                    "CharitiesAccount VARCHAR(20) NOT NULL UNIQUE, " +
                    "CharitiesPassword VARCHAR(20) NOT NULL, " +
                    "CharitiesAddress NVARCHAR(50), " +
                    "CharitiesPhone VARCHAR(10) NOT NULL, " +
                    "CharitiesNo VARCHAR(50) NOT NULL, " +
                    "CharitiesFile NVARCHAR(250) NOT NULL" +
                    ")";

            String eventsTableSql = "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Events' and xtype='U') " +
                    "CREATE TABLE Events (" +
                    "EventID INT IDENTITY(1,1) PRIMARY KEY, " +
                    "CharitiesID INT NOT NULL, " +
                    "EventName NVARCHAR(100) NOT NULL, " +
                    "EventDescription NVARCHAR(1000), " +
                    "EventDate DATE, " +
                    "Location NVARCHAR(200), " +
                    "EventStart TIME, " +
                    "EventEnd TIME, " +
                    "FOREIGN KEY (CharitiesID) REFERENCES Charities(CharitiesID)" +
                    ")";

            String eventParticipantsTableSql = "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='EventParticipants' and xtype='U') " +
                    "CREATE TABLE EventParticipants (" +
                    "ID INT IDENTITY(1,1) PRIMARY KEY, " +
                    "UserID INT NOT NULL, " +
                    "EventID INT NOT NULL, " +
                    "JoinedAt DATETIME DEFAULT GETDATE(), " +
                    "FOREIGN KEY (UserID) REFERENCES Users(UserID), " +
                    "FOREIGN KEY (EventID) REFERENCES Events(EventID)" +
                    ")";

            stmt.executeUpdate(userTableSql);
            stmt.executeUpdate(charityTableSql);
            stmt.executeUpdate(eventsTableSql);
            stmt.executeUpdate(eventParticipantsTableSql);

            System.out.println("資料表建立完成或已存在。");

        } catch (Exception e) {
            System.out.println("建立資料表失敗！");
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Web應用停止時可做清理（這裡可以空著）
    }
}

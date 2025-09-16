package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBInit {

    private static final String URL = "jdbc:sqlserver://localhost:1433;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "tYang0629";

    public static void main(String[] args) {
        try {
            // 1. 連接 master 資料庫（才能建立新資料庫）
            Connection conn = DriverManager.getConnection(URL + ";databaseName=master", USER, PASSWORD);
            Statement stmt = conn.createStatement();

            // 2. 建立 CharityPlatform 資料庫（如果不存在）
            stmt.execute("IF DB_ID('CharityPlatform') IS NULL CREATE DATABASE CharityPlatform");

            // 關閉前面連線，改用 CharityPlatform 資料庫連線
            stmt.close();
            conn.close();

            // 3. 連接 CharityPlatform 資料庫
            conn = DriverManager.getConnection(URL + ";databaseName=CharityPlatform", USER, PASSWORD);
            stmt = conn.createStatement();

            // 4. 建立 Users 表
            String createUsersTable = 
                "IF OBJECT_ID('Users', 'U') IS NULL " +
                "BEGIN " +
                "CREATE TABLE Users (" +
                "UserID INT IDENTITY PRIMARY KEY, " +
                "UserAccount VARCHAR(20) NOT NULL UNIQUE, " +
                "Password VARCHAR(20) NOT NULL, " +
                "FullName NVARCHAR(10) NOT NULL, " +
                "Email VARCHAR(50) NOT NULL, " +
                "Phone VARCHAR(10), " +
                "Address NVARCHAR(100), " +
                "Birthday DATE, " +
                "Gender CHAR(1), " +
                "CreateTime DATETIME DEFAULT GETDATE()" +
                ") " +
                "END";
            stmt.executeUpdate(createUsersTable);

            // 5. 建立 Charities 表
            String createCharitiesTable = 
                "IF OBJECT_ID('Charities', 'U') IS NULL " +
                "BEGIN " +
                "CREATE TABLE Charities (" +
                "CharitiesID INT IDENTITY PRIMARY KEY, " +
                "CharitiesAccount VARCHAR(20) NOT NULL UNIQUE, " +
                "CharitiesPassword VARCHAR(20) NOT NULL, " +
                "CharitiesAddress NVARCHAR(50), " +
                "CharitiesPhone VARCHAR(10) NOT NULL, " +
                "CharitiesNo VARCHAR(50) NOT NULL, " +
                "CharitiesFile NVARCHAR(250) NOT NULL" +
                ") " +
                "END";
            stmt.executeUpdate(createCharitiesTable);

            // 6. 建立 Events 表
            String createEventsTable =
                "IF OBJECT_ID('Events', 'U') IS NULL " +
                "BEGIN " +
                "CREATE TABLE Events (" +
                "EventID INT IDENTITY PRIMARY KEY, " +
                "CharitiesID INT NOT NULL, " +
                "EventName NVARCHAR(100) NOT NULL, " +
                "EventDescription NVARCHAR(1000), " +
                "Location NVARCHAR(200), " +
                "EventStart TIME, " +
                "EventEnd TIME, " +
                "EventDate DATE, " +
                "CONSTRAINT FK_Events_Charities FOREIGN KEY (CharitiesID) REFERENCES Charities(CharitiesID)" +
                ") " +
                "END";
            stmt.executeUpdate(createEventsTable);

            // 7. 建立 EventParticipants 表
            String createEventParticipantsTable =
                "IF OBJECT_ID('EventParticipants', 'U') IS NULL " +
                "BEGIN " +
                "CREATE TABLE EventParticipants (" +
                "ID INT IDENTITY PRIMARY KEY, " +
                "UserID INT NOT NULL, " +
                "EventID INT NOT NULL, " +
                "JoinedAt DATETIME DEFAULT GETDATE(), " +
                "CONSTRAINT FK_EventParticipants_Users FOREIGN KEY (UserID) REFERENCES Users(UserID), " +
                "CONSTRAINT FK_EventParticipants_Events FOREIGN KEY (EventID) REFERENCES Events(EventID)" +
                ") " +
                "END";
            stmt.executeUpdate(createEventParticipantsTable);

            // 關閉資源
            stmt.close();
            conn.close();

            System.out.println("✅ 資料庫與資料表初始化成功");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

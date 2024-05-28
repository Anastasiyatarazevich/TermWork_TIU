package org.example;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:company.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void createNewDatabase() {
        try (Connection conn = DriverManager.getConnection(URL)) {
            if (conn != null) {
                System.out.println("A new database has been created.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createTables() {
        String sqlEmployees = "CREATE TABLE IF NOT EXISTS employees (\n"
                + " EmployeeID INTEGER PRIMARY KEY,\n"
                + " FirstName TEXT NOT NULL,\n"
                + " LastName TEXT NOT NULL,\n"
                + " Position TEXT,\n"
                + " Department TEXT,\n"
                + " ContactInfo TEXT,\n"
                + " HireDate TEXT,\n"
                + " Salary REAL,\n"
                + " Address TEXT,\n"
                + " PassportInfo TEXT,\n"
                + " DateOfBirth TEXT,\n"
                + " ManagerID INTEGER,\n"
                + " FOREIGN KEY (ManagerID) REFERENCES employees (EmployeeID)\n"
                + ");";

        String sqlProjects = "CREATE TABLE IF NOT EXISTS projects (\n"
                + " ProjectID INTEGER PRIMARY KEY,\n"
                + " ProjectName TEXT NOT NULL,\n"
                + " Description TEXT,\n"
                + " StartDate TEXT,\n"
                + " EndDate TEXT,\n"
                + " AssignedEmployeeID INTEGER,\n"
                + " Status TEXT,\n"
                + " FOREIGN KEY (AssignedEmployeeID) REFERENCES employees (EmployeeID)\n"
                + ");";

        String sqlTasks = "CREATE TABLE IF NOT EXISTS tasks (\n"
                + " TaskID INTEGER PRIMARY KEY,\n"
                + " TaskName TEXT NOT NULL,\n"
                + " Description TEXT,\n"
                + " StartDate TEXT,\n"
                + " EndDate TEXT,\n"
                + " AssignedEmployeeID INTEGER,\n"
                + " ProjectID INTEGER,\n"
                + " Status TEXT,\n"
                + " FOREIGN KEY (AssignedEmployeeID) REFERENCES employees (EmployeeID),\n"
                + " FOREIGN KEY (ProjectID) REFERENCES projects (ProjectID)\n"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sqlEmployees);
            stmt.execute(sqlProjects);
            stmt.execute(sqlTasks);
            System.out.println("Tables created successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        createNewDatabase();
        createTables();
    }
}

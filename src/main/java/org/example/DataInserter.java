package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataInserter {
    private Connection conn;

    public DataInserter(Connection conn) {
        this.conn = conn;
    }

    public void insertEmployee(int id, String firstName, String lastName, String position, String department, String contactInfo, String hireDate, double salary, String address, String passportInfo, String dateOfBirth, Integer managerID) {
        String sql = "INSERT INTO employees(EmployeeID, FirstName, LastName, Position, Department, ContactInfo, HireDate, Salary, Address, PassportInfo, DateOfBirth, ManagerID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, position);
            pstmt.setString(5, department);
            pstmt.setString(6, contactInfo);
            pstmt.setString(7, hireDate);
            pstmt.setDouble(8, salary);
            pstmt.setString(9, address);
            pstmt.setString(10, passportInfo);
            pstmt.setString(11, dateOfBirth);
            if (managerID != null) {
                pstmt.setInt(12, managerID);
            } else {
                pstmt.setNull(12, java.sql.Types.INTEGER);
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertProject(int id, String projectName, String description, String startDate, String endDate, int assignedEmployeeID, String status) {
        String sql = "INSERT INTO projects(ProjectID, ProjectName, Description, StartDate, EndDate, AssignedEmployeeID, Status) VALUES(?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, projectName);
            pstmt.setString(3, description);
            pstmt.setString(4, startDate);
            pstmt.setString(5, endDate);
            pstmt.setInt(6, assignedEmployeeID);
            pstmt.setString(7, status);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertTask(int id, String taskName, String description, String startDate, String endDate, int assignedEmployeeID, int projectID, String status) {
        String sql = "INSERT INTO tasks(TaskID, TaskName, Description, StartDate, EndDate, AssignedEmployeeID, ProjectID, Status) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, taskName);
            pstmt.setString(3, description);
            pstmt.setString(4, startDate);
            pstmt.setString(5, endDate);
            pstmt.setInt(6, assignedEmployeeID);
            pstmt.setInt(7, projectID);
            pstmt.setString(8, status);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        Connection conn = DatabaseManager.connect();
        DataInserter dataInserter = new DataInserter(conn);

        // Вставка данных в таблицу employees
        dataInserter.insertEmployee(1, "John", "Doe", "Manager", "HR", "john.doe@example.com", "2020-01-15", 60000.0, "123 Main St", "123456789", "1985-05-20", null);
        dataInserter.insertEmployee(2, "Jane", "Smith", "Developer", "IT", "jane.smith@example.com", "2019-03-10", 80000.0, "456 Elm St", "987654321", "1990-08-25", 1);

        // Вставка данных в таблицу projects
        dataInserter.insertProject(1, "Project Alpha", "First project", "2021-06-01", "2021-12-31", 1, "In Progress");

        // Вставка данных в таблицу tasks
        dataInserter.insertTask(1, "Task 1", "First task for Project Alpha", "2021-06-01", "2021-06-15", 2, 1, "Not Started");

        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

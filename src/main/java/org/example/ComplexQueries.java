package org.example;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ComplexQueries {
    private Connection conn;

    public ComplexQueries(Connection conn) {
        this.conn = conn;
    }

    public void getTasksByEmployee(int employeeID) {
        String sql = "SELECT TaskID, TaskName, Status FROM tasks WHERE AssignedEmployeeID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, employeeID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("Task ID: " + rs.getInt("TaskID") + ", Task Name: " + rs.getString("TaskName") + ", Status: " + rs.getString("Status"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getProjectsAndEmployees() {
        String sql = "SELECT projects.ProjectName, employees.FirstName, employees.LastName "
                + "FROM projects "
                + "JOIN employees ON projects.AssignedEmployeeID = employees.EmployeeID";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("Project Name: " + rs.getString("ProjectName") + ", Assigned Employee: " + rs.getString("FirstName") + " " + rs.getString("LastName"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getDocumentsByProject(int projectID) {
        String sql = "SELECT DocumentID, DocumentName, Status FROM documents WHERE RelatedProjectID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, projectID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("Document ID: " + rs.getInt("DocumentID") + ", Document Name: " + rs.getString("DocumentName") + ", Status: " + rs.getString("Status"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

package org.example;



import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Connection conn = DatabaseManager.connect();
        if (conn == null) {
            System.out.println("Connection to database failed.");
            return;
        }

        ComplexQueries queries = new ComplexQueries(conn);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Выберите запрос:");
            System.out.println("1. Получить задачи по сотруднику");
            System.out.println("2. Получить проекты и их ответственных сотрудников");
            System.out.println("3. Получить документы по проекту");
            System.out.println("4. Выйти");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Введите ID сотрудника: ");
                    int employeeID = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    queries.getTasksByEmployee(employeeID);
                    break;
                case 2:
                    queries.getProjectsAndEmployees();
                    break;
                case 3:
                    System.out.print("Введите ID проекта: ");
                    int projectID = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    queries.getDocumentsByProject(projectID);
                    break;
                case 4:
                    System.out.println("Выход...");
                    closeConnection(conn);
                    scanner.close();
                    return;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
            }

            System.out.println();
        }
    }

    private static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

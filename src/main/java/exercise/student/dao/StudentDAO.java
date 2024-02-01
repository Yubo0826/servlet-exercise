package exercise.student.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import exercise.student.model.Student;


public class StudentDAO {
    
    public Boolean addStudent(Student student) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO students (name, age) VALUES (?, ?)")) {

            statement.setString(1, student.getName());
            statement.setInt(2, student.getAge());
            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM students");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setName(resultSet.getString("name"));
                student.setAge(resultSet.getInt("age"));
                students.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    public Student getStudentById(int studentId) {
        Student student = null;
        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM students WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, studentId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        // 从结果集中提取学生信息
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        int age = resultSet.getInt("age");
    
                        // 创建 Student 对象
                        student = new Student();
                        student.setId(id);
                        student.setName(name);
                        student.setAge(age);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return student;

    }

    public Boolean updateStudent(Student updatedStudent) {
        try (Connection connection = getConnection()) {
            String sql = "UPDATE students SET name = ?, age = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, updatedStudent.getName());
                statement.setInt(2, updatedStudent.getAge());
                statement.setInt(3, updatedStudent.getId());

                // 执行更新操作
                int rowsAffected = statement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public void deleteStudent(int id) {
        try (Connection connection = getConnection()) {
            String sql = "DELETE FROM students WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Connection getConnection() throws SQLException {
        final String url = "jdbc:mysql://localhost:3306/servelet_exercise";
        final String username = "root";
        final String password = "qaz6699wsx";
    
        // 注册MySQL驱动程序
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("MySQL JDBC驱动未找到");
        }
    
        // 获取数据库连接
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection;
    }
}

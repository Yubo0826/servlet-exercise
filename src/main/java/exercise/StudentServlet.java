package exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import exercise.student.dao.StudentDAO;
import exercise.student.model.Student;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet("/students")
public class StudentServlet extends HttpServlet {
    private StudentDAO studentDAO = new StudentDAO();

    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 设置 CORS 头信息以允许预检请求
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        // 设置预检请求的有效期，单位：秒
        response.setHeader("Access-Control-Max-Age", "3600");

        response.setStatus(HttpServletResponse.SC_OK);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 设置CORS头
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        response.setContentType("application/json");

        List<Student> students = studentDAO.getAllStudents();
        String json = new Gson().toJson(students);
        response.getWriter().write(json);
        // request.setAttribute("students", students);
        // request.getRequestDispatcher("/students.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        try {
          if ("add".equals(action)) {
              addStudent(request, response);
          } else if ("edit".equals(action)) {
              editStudent(request, response);
              return;
          } else if ("update".equals(action)) {
              updateStudent(request, response);
              return;
          } else if ("delete".equals(action)) {
              deleteStudent(request);
          } 
        } catch (NumberFormatException e) {
            // 处理参数转换异常
            request.setAttribute("error", "Invalid input. Please enter valid data.");
        }

        // response.sendRedirect(request.getContextPath() + "/students");

    }


    private void addStudent(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
      System.out.println("addStudent");

      try {
        // 读取请求体的内容
        StringBuilder requestBody = new StringBuilder();
        BufferedReader reader = request.getReader();
        System.out.println(reader);
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        System.out.println(requestBody);
  
        // 解析JSON数据
        JsonObject jsonObject = JsonParser.parseString(requestBody.toString()).getAsJsonObject();
  
        // 从JSON中获取数据
        String name = jsonObject.get("name").getAsString();
        int age = jsonObject.get("age").getAsInt();
  
  
  
        // String name = request.getParameter("name");
        // int age = Integer.parseInt(request.getParameter("age"));
        Student newStudent = new Student();
        newStudent.setName(name);
        newStudent.setAge(age);
        Boolean result = studentDAO.addStudent(newStudent);
  
        Gson gson = new Gson();
        String json = gson.toJson(result);
        response.getWriter().write(json);        
      } catch (Exception e) {
        e.printStackTrace();
        throw new ServletException("Error processing the request.", e);
      }


    }


    private void editStudent(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
      int id = Integer.parseInt(request.getParameter("id"));
      Student existingStudent = studentDAO.getStudentById(id);
      request.setAttribute("student", existingStudent);
      request.getRequestDispatcher("/edit.jsp").forward(request, response);
    }


    private void updateStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        Student updatedStudent = new Student();
        updatedStudent.setId(id);
        updatedStudent.setName(name);
        updatedStudent.setAge(age);
        boolean updateResult = studentDAO.updateStudent(updatedStudent);
        request.setAttribute("updateResult", updateResult);
        RequestDispatcher dispatcher = request.getRequestDispatcher("edit.jsp");
        dispatcher.forward(request, response);
    }

    private void deleteStudent(HttpServletRequest request) {
      int id = Integer.parseInt(request.getParameter("id"));
      studentDAO.deleteStudent(id);
  }
}


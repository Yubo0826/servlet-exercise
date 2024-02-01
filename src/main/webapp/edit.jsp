<!-- edit.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
    <title>Edit Student</title>
</head>
<body>
    <h2>Edit Student</h2>
    <form action="?action=update" method="post">
        <input type="hidden" name="id" value="${student.id}">
        Name: <input type="text" name="name" value="${student.name}" required><br>
        Age: <input type="number" name="age" value="${student.age}" required><br>
        <input type="submit" value="Update">
    </form>

    <%
      // 获取从服务器传递过来的参数
      String studentIdStr = request.getParameter("studentId");
      int studentId = Integer.parseInt(studentIdStr);
      String studentName = request.getParameter("studentName");
      int studentAge = Integer.parseInt(request.getParameter("studentAge"));

      // 创建一个 Student 对象
      Student student = new Student();
      student.setId(studentId);
      student.setName(studentName);
      student.setAge(studentAge);

      // 调用 updateStudent 方法更新学生信息
      StudentDAO studentDAO = new StudentDAO();
      boolean updateResult = studentDAO.updateStudent(student);
    %>

    <div>
        <h2>Edit Student</h2>
        <% if (updateResult) { %>
            <p>Student information updated successfully!</p>
        <% } else { %>
            <p>Failed to update student information.</p>
        <% } %>
        <a href="students.jsp">Back to Students List</a>
    </div>
</body>
</html>

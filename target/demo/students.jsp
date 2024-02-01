<!-- students.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> --%>
<html>
<head>
    <title>Student Management System</title>
</head>
<body>
    <%
      out.println("Your students is " + request.getAttribute("students"));
    %>
    <h2>Students</h2>
    <div>${students}</div>
    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Age</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="student" items="${students}">
                <tr>
                    <td>${student.id}</td>
                    <td>${student.name}</td>
                    <td>${student.age}</td>
                    <td>
                        <a href="?action=edit&id=${student.id}">Edit</a>
                        <a href="?action=delete&id=${student.id}">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <h2>Add Student</h2>
    <form action="?action=add" method="post">
        Name: <input type="text" name="name" required><br>
        Age: <input type="number" name="age" required><br>
        <input type="submit" value="Add">
    </form>
</body>
</html>

package exercise;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 模拟数据库中的用户凭据
        String validUsername = "user";
        String validPassword = "123";

        // 从请求中获取用户名和密码
        String enteredUsername = request.getParameter("username");
        String enteredPassword = request.getParameter("password");

        // 验证用户名和密码
        if (validUsername.equals(enteredUsername) && validPassword.equals(enteredPassword)) {
            // 验证成功，将用户信息存储到 Session 中
            HttpSession session = request.getSession();
            session.setAttribute("username", enteredUsername);

            // 重定向到欢迎页面
            response.sendRedirect(request.getContextPath() + "/welcome.jsp");
        } else {
            // 验证失败，重定向回登入页面
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=true");
        }
    }
}

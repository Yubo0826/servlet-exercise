package exercise;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

@WebServlet("/json")
public class JsonServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 设置响应内容类型为 JSON
            response.setContentType("application/json");

            // 创建 JSON 对象
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", "John");
            jsonObject.put("age", 30);
            jsonObject.put("city", "New York");

            // 将 JSON 字符串作为响应写入输出流
            response.getWriter().write(jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


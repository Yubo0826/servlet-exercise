import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

@WebServlet("/processData")
public class DataProcessingServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 从请求中读取客户端发送的数据
            String requestData = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);

            // 将接收到的 JSON 数据转换为 JSONObject
            JSONObject jsonObject = new JSONObject(requestData);

            // 根据不同的数据进行处理
            String responseType = "";
            if (jsonObject.has("command")) {
                String command = jsonObject.getString("command");
                switch (command) {
                    case "greet":
                        responseType = "Hello, " + jsonObject.getString("name") + "!";
                        break;
                    case "calculate":
                        int result = jsonObject.getInt("operand1") + jsonObject.getInt("operand2");
                        responseType = "Result: " + result;
                        break;
                    default:
                        responseType = "Unknown command";
                }
            } else {
                responseType = "Command not provided";
            }

            // 设置响应内容类型为 JSON
            response.setContentType("application/json");

            // 创建 JSON 对象并设置响应内容
            JSONObject responseJson = new JSONObject();
            responseJson.put("response", responseType);
            response.getWriter().write(responseJson.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

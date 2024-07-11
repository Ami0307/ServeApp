import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class GetPersonNumServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String camara_location = request.getParameter("camara_location");

        try {
            Connection connect = DBUtil.getConnect();
            Statement statement = connect.createStatement(); // Statement可以理解为数据库操作实例，对数据库的所有操作都通过它来实现

            String sqlQuery = "select person_num from capture_data where camara_location = '" + camara_location +
                    "'order by rid desc";
            ResultSet result = statement.executeQuery(sqlQuery);

            while(result.next()){
                String person_num = result.getString("person_num");

                HashMap<String, String> map = new HashMap<>();
                map.put("person_num",person_num);

                Gson gson = new Gson();
                response.setContentType("text/html;charset=utf-8"); // 设置响应报文的编码格式
                PrintWriter pw = response.getWriter(); // 获取 response 的输出流
                pw.println(gson.toJson(map)); // 通过输出流把业务逻辑的结果输出
                pw.flush();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doGet(request, response);
    }
}

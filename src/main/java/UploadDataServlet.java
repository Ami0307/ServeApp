import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class UploadDataServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String Code = "";
        String person_num = request.getParameter("person_num");
        String camara_location = request.getParameter("camara_location");
        String time = request.getParameter("time");
        String date = request.getParameter("date");

        try {
            Connection connect = DBUtil.getConnect();
            Statement statement = connect.createStatement(); // Statement可以理解为数据库操作实例，对数据库的所有操作都通过它来实现

            String sqlInsertPass = "insert into capture_data (person_num,camara_location,time,date) " +
                    "values('"+person_num+"','"+ camara_location +"','"+time+"','"+ date +"')";
            int row1 = statement.executeUpdate(sqlInsertPass);
            if(row1 == 1){
                Code = "0";
            }
            else {
                Code = "-1";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("Code", Code);

        Gson gson = new Gson();
        response.setContentType("text/html;charset=utf-8"); // 设置响应报文的编码格式
        PrintWriter pw = response.getWriter(); // 获取 response 的输出流
        pw.println(gson.toJson(map)); // 通过输出流把业务逻辑的结果输出
        pw.flush();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doGet(request, response);
    }
}

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class rentee {
    int id,storeNo;
    String name, phoneNo, contStartDate, contEndDate;

    public Connection connection = null;
    public Statement statement= null;
    public ResultSet resultSet= null;
    public PreparedStatement preparedStatement= null;
}

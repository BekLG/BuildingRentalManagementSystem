import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class store {
    int storeNo,floorNo;
    float area,price;
    boolean rented;

    public Connection connection = null;
    public Statement statement= null;
    public ResultSet resultSet= null;
    public PreparedStatement preparedStatement= null;
}

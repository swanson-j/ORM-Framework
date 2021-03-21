import orm.config.JDBCConnection;
import orm.config.JDBCConnectionPool;

import java.io.IOException;
import java.sql.SQLException;

public class Driver {

    public static void main(String[] args) throws IOException, SQLException {
        JDBCConnection connection = JDBCConnection.getInstance("src/main/resources/jdbc.config");
        JDBCConnectionPool.createPool();
    }
}

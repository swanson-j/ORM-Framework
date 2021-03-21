package orm.config;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCConnectionPool {
    public static int POOL_SIZE = 5;
    public static List<Connection> availableConnections;
    public static List<Connection> currentlyUsedConnections = new ArrayList<>();
    private static JDBCConnectionPool connectionPool;

    private JDBCConnectionPool(){}

    public static void createPool() throws IOException, SQLException {
        availableConnections = new ArrayList<>();
        for(int i = 0; i < POOL_SIZE; i++){
            availableConnections.add(JDBCConnection.getInstance().getConnection());
        }
    }

    public static Connection getConnection(){
        Connection connection = availableConnections.remove(availableConnections.size() - 1);
        currentlyUsedConnections.add(connection);
        return connection;
    }

    public static boolean releaseConnection() throws IOException, SQLException {
        Connection connection = JDBCConnection.getInstance().getConnection();
        availableConnections.add(connection);
        return currentlyUsedConnections.remove(connection);
    }
}

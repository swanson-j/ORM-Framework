package orm.config;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/*
 *  Implementation of a connection pool that can be called when a connection is first established.
 */

public class JDBCConnectionPool {
    public static int POOL_SIZE = 5;
    public static List<Connection> availableConnections;
    public static List<Connection> currentlyUsedConnections = new ArrayList<>();
    private static JDBCConnectionPool connectionPool;

    private JDBCConnectionPool(){}

    /*
     *  createPool() is called by a developer after first establishing a connection.
     *      This fills a list<Connection> of size POOL_SIZE with connections
     *      made with JDBCConnection singleton with a path to jdbc config file
     */
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

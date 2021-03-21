package orm.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 *  Used to configure a JDBC Connection by parsing
 *  a file and storing the properties in a map
 */

public class JDBCConnection {

    private static JDBCConnection instance;
    private static Path connectionPath;
    private static List<String> properties;


    private JDBCConnection(){}

    public static void setProperties(List<String> properties) {
        JDBCConnection.properties = properties;
    }

    public static void printProperties(){
        for(String property : properties){
            System.out.println(property);
        }
    }

    public Path getConnectionPath() {
        return connectionPath;
    }

    public void setConnectionPath(Path connectionPath) {
        this.connectionPath = connectionPath;
    }

    /*
     *  Creates initial connection
     *  TODO: Establish the connection with the properties
     */
    public static JDBCConnection getInstance(String path) throws IOException {
        if(instance == null || properties != null){
            connectionPath = Paths.get(path);
            getProperties();
            instance = new JDBCConnection();
        }
        return instance;
    }

    /*
     *  After initial creation of connection is made, user can now call this method
     *      with no Path string parameter
     */
    public static JDBCConnection getInstance() throws IOException {
        if(instance == null){
            throw new IOException("You need to create a connection with a path");
        }
        return instance;
    }

    /*
     *  Retrieves properties from the path file and stores them in properties map
     */
    public static void getProperties() throws IOException {
        properties = new ArrayList<>();
        ByteBuffer buffer = ByteBuffer.allocate(48);
        FileInputStream inStream = new FileInputStream(connectionPath.toFile());
        inStream.getChannel().read(buffer);
        List<String> lines = Files.readAllLines(connectionPath);
        lines.stream().forEach((String line) -> {
            properties.add(line);
        });
    }

    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection(
                properties.get(0),
                properties.get(1),
                properties.get(2)
        );
    }

}

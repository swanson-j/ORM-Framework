package orm.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    static List<String> properties;


    private JDBCConnection(){}

    public Path getConnectionPath() {
        return connectionPath;
    }

    public void setConnectionPath(Path connectionPath) {
        this.connectionPath = connectionPath;
    }

    /*
     *  Creates initial connection
     */
    public static JDBCConnection getConnection(String path) throws IOException {
        if(instance == null){
            connectionPath = Paths.get(path);
            getProperties();
        }
        return instance;
    }

    /*
     *  After initial creation of connection is made, user can now call this method
     *      with no Path string parameter
     */
    public static JDBCConnection getConnection() throws IOException {
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

        // Logging
        properties.stream().forEach(System.out::println);
    }

}

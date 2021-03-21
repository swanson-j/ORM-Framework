import orm.config.JDBCConnection;

import java.io.IOException;

public class Driver {

    public static void main(String[] args) throws IOException {
        JDBCConnection connection = JDBCConnection.getConnection("src/main/resources/jdbc.config");

    }
}

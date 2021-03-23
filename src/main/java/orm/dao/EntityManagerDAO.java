package orm.dao;

import orm.config.JDBCConnection;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class EntityManagerDAO implements InterfaceDAO<String> {
    @Override
    public int save(String sql) {
        try {
            PreparedStatement preparedStatement = JDBCConnection.getInstance().getConnection().prepareStatement(sql);
            int i = preparedStatement.executeUpdate();
            System.out.println("The number of updated rows were: " + i);
            return i;
        } catch (SQLException throwables) {
            String throwableMessage = throwables.getMessage();
            if(throwableMessage.substring(0,26).equals("ERROR: duplicate key value")){
                System.out.println("The number of updated rows were: 0");
                return 0;
            } else {
                throwables.printStackTrace();
                return -1;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List read(String s) {
        return null;
    }

    @Override
    public int update(String s) {
        return 0;
    }

    @Override
    public boolean destroy(String s) {
        return false;
    }
}



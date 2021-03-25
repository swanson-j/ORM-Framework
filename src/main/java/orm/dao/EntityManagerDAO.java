package orm.dao;

import orm.config.JDBCConnection;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EntityManagerDAO implements InterfaceDAO<String> {
    @Override
    public int save(String sql) {
        try {
            PreparedStatement preparedStatement = JDBCConnection.getInstance().getConnection().prepareStatement(sql);
            int i = preparedStatement.executeUpdate();
            System.out.println("The number of updated rows were: " + i);
            return i;
        } catch (SQLException throwable) {
            String throwableMessage = throwable.getMessage();
            if(throwableMessage.substring(0,26).equals("ERROR: duplicate key value")){
                System.out.println("The number of updated rows were: 0");
                return 0;
            } else {
                throwable.printStackTrace();
                return -1;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public ResultSet read(String sql) {

        try {
            PreparedStatement preparedStatement = JDBCConnection.getInstance().getConnection().prepareStatement(sql);
            return preparedStatement.executeQuery();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int update(String sql) {
        try {
            PreparedStatement preparedStatement = JDBCConnection.getInstance().getConnection().prepareStatement(sql);
            return preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public boolean destroy(String sql) {
        try {
            PreparedStatement preparedStatement = JDBCConnection.getInstance().getConnection().prepareStatement(sql);
            int i = preparedStatement.executeUpdate();

            return i > 0;
        } catch (SQLException throwable) {
            System.out.println("Maintaining referential integrity: try killing orphans");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}



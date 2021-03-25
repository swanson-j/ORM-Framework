package orm.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface InterfaceDAO<T> {
    public int save(T t);
    public ResultSet read(T t);
    public int update(T t);
    public boolean destroy(T t) throws IOException, SQLException;
}

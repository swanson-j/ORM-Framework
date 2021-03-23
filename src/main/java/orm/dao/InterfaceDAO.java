package orm.dao;

import java.util.List;

public interface InterfaceDAO<T> {
    public int save(T t);
    public List<T> read(T t);
    public int update(T t);
    public boolean destroy(T t);
}

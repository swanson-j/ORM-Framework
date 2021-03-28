package orm.utilities;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import orm.dao.EntityManagerDAO;
import orm.model.RayGun;
import orm.service.EntityManager;

import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ResultSetHandlerTest {

    ResultSetHandler resultSetHandler = new ResultSetHandler();

    EntityManagerDAO entityManagerDAO = Mockito.mock(EntityManagerDAO.class);

    @Test
    void handleRead() throws Exception {
        RayGun raygun = null;
        when(entityManagerDAO.read("test")).thenReturn(null);
        assertEquals(null, resultSetHandler.handleRead(RayGun.class, "test"));
    }
}
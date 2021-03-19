package orm.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CupTest {

    public Cup cup = new Cup("yellow", 5);

    @Test
    void getColor() {
        assertEquals("yellow", cup.getColor());
    }

    @Test
    void setColor() {
        cup.setColor("green");
        assertEquals("green", cup.getColor());
    }

    @Test
    void getHeight() {
        assertEquals(5, cup.getHeight());
    }

    @Test
    void setHeight() {
        cup.setHeight(4);
        assertEquals(4, cup.getHeight());
    }
}
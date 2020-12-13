package ex2.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgeDataTest {
    EdgeData e = new EdgeData(1,2,5);

    @Test
    void getSrc() {
        assertEquals(1,e.getSrc());

    }

    @Test
    void getDest() {

            assertEquals(2,e.getDest());
    }

    @Test
    void getWeight() {

            assertEquals(5,e.getWeight());
    }

//    @Test
//    void getInfo() {
//    }
//
//    @Test
//    void setInfo() {
//    }
//
//    @Test
//    void getTag() {
//    }
//
//    @Test
//    void setTag() {
//    }
}
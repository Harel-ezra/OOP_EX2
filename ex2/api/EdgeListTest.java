package ex2.api;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EdgeListTest {
    EdgeList list = new EdgeList();


    @Test
    void addNi() {
        list.addNi(new EdgeData(1,1,5));
        list.addNi(new EdgeData(1,2,6));


    }



    @Test
    void getE() {
        list.getE(1);
    }

    @Test
    void hasE() {
        assertEquals(true,list.hasE(2));
        assertEquals(false,list.hasE(3));
    }


    @Test
    void removeE() {
        list.removeE(2);
        assertEquals(false,list.hasE(2));
    }

}
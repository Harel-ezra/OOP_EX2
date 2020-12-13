package ex2.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeDataTest {
    node_data n=new NodeData(0,1,1,1);
    @Test
    void getKey() {
        assertEquals(0,n.getKey());

    }

    @Test
    void getLocation() {



    }

    @Test
    void setLocation() {
    }


    @Test
    void getInfo() {
        assertEquals("x",n.getInfo());
    }

    @Test
    void setInfo() {
        n.setInfo("test");
        assertEquals("test",n.getInfo());
    }

    @Test
    void getTag() {
        assertEquals(-1,n.getTag());
    }

    @Test
    void setTag() {
        n.setTag(1);
        assertEquals(1,n.getInfo());
    }
}
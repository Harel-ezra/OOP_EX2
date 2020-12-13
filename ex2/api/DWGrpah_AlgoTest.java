package ex2.api;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DWGrpah_AlgoTest {


    directed_weighted_graph graphForTest() {
        directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new NodeData(0,1,2,3));
        g.addNode(new NodeData(1,1,2,3));
        g.addNode(new NodeData(2,1,2,3));
        g.addNode(new NodeData(3,1,2,3));
        g.addNode(new NodeData(4,1,2,3));
        g.addNode(new NodeData(5,1,2,3));
        g.addNode(new NodeData(6,1,2,3));
        g.addNode(new NodeData(7,1,2,3));
        g.addNode(new NodeData(8,1,2,3));
        g.addNode(new NodeData(9,1,2,3));
        g.addNode(new NodeData(10,1,2,3));
        g.connect(0, 5, 1);
        g.connect(0, 2, 4.7);
        g.connect(0, 4, 1.75);
        g.connect(4, 17, 0);
        g.connect(5, 704, 2);
        g.connect(2, 5, 17);
        g.connect(1, 2, 0.825);
        g.connect(1, 17, 0.5);
        g.connect(2, 12, 7.05);
        g.connect(2, 45, 3);
        g.connect(45, 12, 4.05);
        g.connect(45, 8, 4.02);
        g.connect(12, 8, 2.08);
        g.connect(8, 79, 1.08);
        g.connect(12, 79, 1.05);
        g.connect(45, 704, 2.07);
        g.connect(2, 17, 1.7);
        return g;
    }
    @Test
    void shortestPathDist() {
        directed_weighted_graph g = graphForTest();
        dw_graph_algorithms ga1 = new DWGrpah_Algo();
        ga1.init(g);
        assertEquals(3.075, ga1.shortestPathDist(0, 2));
        assertEquals(10.17, ga1.shortestPathDist(0, 79));
        assertEquals(5.07, ga1.shortestPathDist(0, 45));
        assertEquals(1.325, ga1.shortestPathDist(2, 17));
        ga1.getGraph().connect(4, 17, 70215);
        assertEquals(5.07, ga1.shortestPathDist(0, 45));
    }

    @Test
    void connectivity() {
//        directed_weighted_graph g = graphForTest();
//        dw_graph_algorithms ga1 = new DWGrpah_Algo();
//        ga1.init(g);
//        assertTrue(ga1.isConnected());
//        g.removeEdge(1, 2);
//        g.removeEdge(1, 17);
//        assertTrue(!ga1.isConnected());
//        g.removeNode(1);
//        assertTrue(ga1.isConnected());
//        g.removeNode(45);
//        assertTrue(ga1.isConnected());
//        g.connect(8, 704, 597);
//        g.removeNode(5);
//        assertTrue(ga1.isConnected());

    }


    @org.junit.jupiter.api.Test
    void save() {
        directed_weighted_graph g=new DWGraph_DS();
        dw_graph_algorithms ga=new DWGrpah_Algo();
        ga.init(g);
        g.addNode(new NodeData(1,0,0,0));
        g.addNode(new NodeData(2,1,0,0));
        g.addNode(new NodeData(3,2,0,0));
        g.addNode(new NodeData(4,3,0,0));
        g.addNode(new NodeData(5,4,0,0));
        g.connect(1,2,1);
        g.connect(1,3,1);
        g.connect(1,5,5);
        g.connect(2,1,0.4);
        if(g.getEdge(2,1)!=null) {
            System.out.println(g.getEdge(2,1).getWeight());
        }
        else
        {
            System.out.println("null");
        }
        System.out.println(ga.save("test"));

    }

    @org.junit.jupiter.api.Test
    void load() {
        DWGrpah_Algo g = new DWGrpah_Algo();
        g.load("test");
        assertEquals(5,g.getGraph().nodeSize());
        assertEquals(4,g.getGraph().edgeSize());
    }
}
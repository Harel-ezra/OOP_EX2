package ex2.api;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_DSTest {
    private static Random random = null;

    /**
     * test for getNode method, creat a graph and ask for some nodes, some exist and some not.
     */
    @Test
    void getNode() {
        directed_weighted_graph g=graph_creator(20,40,1);
        int getNodeCounter=0;
        for(int i=-5; i<33;i+=5)
        {
            node_data n= g.getNode(i);
            if(n!=null)
            {
                getNodeCounter++;
            }
        }
        assertEquals(4,getNodeCounter);
    }

    /**
     * test for hasEdge method, creat a graph, connect some node with an edge and check if the are exist.
     */
    @Test
    void hasEdge() {
        directed_weighted_graph g=graph_creator(20,0,1);
        g.connect(0,1,15);
        g.connect(0,-1,2.5);
        g.connect(3,5,-3);
        g.connect(3,5,0.23);
        g.connect(0,1,4);
        g.connect(0,9,4);
        g.connect(5,8,4);
        int hasEdgeCounter=0;
        for (int i=-1;i<25;i++)
        {
            if(g.getEdge(0,i)!=null)
                hasEdgeCounter++;
            if(g.getEdge(i,5)!=null)
                hasEdgeCounter++;
        }
        assertEquals(4,hasEdgeCounter);
    }

    /**
     * test for getEdge method for checking if the distance that connected its really is
     */
    @Test
    void getEdge() {
        directed_weighted_graph g=graph_creator(20,0,1);
        g.connect(0,1,15);
        g.connect(0,-1,2.5);
        g.connect(3,5,-3);
        g.connect(3,5,0.23);
        g.connect(0,1,4);
        g.connect(0,9,4);
        g.connect(5,8,4);
        int getEdgeCounter=0;
        for (int i=-1;i<25;i++)
        {
            if(g.getEdge(0,i)!=null)
                getEdgeCounter++;
            if(g.getEdge(i,5)!=null)
                getEdgeCounter++;
        }
        assertEquals(4,getEdgeCounter);
    }

    /**
     * test for addNode, creat graph, add some node and check if its success
     */
    @Test
    void addNode() {
        directed_weighted_graph g=graph_creator(20,0,1);
        int addNodeCounter=0;
        for (int i=-13;i<50;i+=7)
            g.addNode(new NodeData(i,i,i,i));
        assertEquals(g.nodeSize(),26);
    }

    /**
     * test for connect node at the graph
     */
    @Test
    void connect() {
        directed_weighted_graph g=graph_creator(20,0,1);
        g.connect(0,1,15);
        g.connect(0,-1,2.5);
        g.connect(3,5,0.23);
        g.connect(0,1,4);
        g.connect(0,9,4);
        g.connect(5,8,4);
        assertEquals(g.getEdge(0,1),4);
        assertEquals(g.getEdge(5,3),0.23);
        g.removeEdge(5,3);
        assertEquals(g.getEdge(5,3),-1);
        g.connect(3,5,-3);
        assertEquals(g.getEdge(5,3),-1);
        assertFalse(g.getEdge(5,3)!=null);
        assertTrue(g.getEdge(0,1)!=null);
        assertFalse(g.getEdge(0,-1)!=null);

    }

    @Test
    void getV() {
        directed_weighted_graph g=graph_creator(20,50,1);
        Collection<node_data> col=g.getV();
        assertEquals(col.size(),20);
        for(int i=0;i<20;i++)
        {
            assertTrue(col.contains(g.getNode(i)));
        }
        assertFalse(col.contains(-5));
        assertFalse(col.contains(22));
    }

    @Test
    void testGetV() {
        directed_weighted_graph g=new DWGraph_DS();
        for (int i=0;i<15;i++)
        {
            g.addNode(new NodeData(i,i,i,i));
        }
        for (int i=0;i<15;i+=3)
        {
            g.connect(1,i,i+0.33);
        }
        Collection<node_data> col=g.getV();
        assertEquals(col.size(),5);
        assertTrue(col.contains(g.getNode(6)));
        assertFalse(col.contains(g.getNode(1)));
        assertFalse(col.contains(g.getNode(2)));
    }

    /**
     * test for remove node.
     * remove some node and check if its done
     */
    @Test
    void removeNode() {
        directed_weighted_graph g=graph_creator(20,50,1);
        node_data n=g.removeNode(-1);
        assertEquals(n,null);
        g.removeNode(3);
        assertEquals(g.nodeSize(),19);
        for(int i=22;i>-4;i-=4)
        {
            g.removeNode(i);
        }
        assertEquals(g.nodeSize(),14);
        assertFalse(g.getEdge(18,15)!=null);
        g.removeNode(0);
        g.removeNode(0);
        assertFalse(g.getEdge(0,15)!=null);
        assertFalse(g.getEdge(0,0)!=null);
    }

    /**
     * test for remove edge.
     * remove some edge and check if its done
     */
    @Test
    void removeEdge() {
        directed_weighted_graph g=graph_creator(20,0,1);
        g.connect(0,1,5);
        g.removeEdge(0,1);
        assertEquals(g.edgeSize(),0);
        for(int i=0;i<20;i++)
        {
            g.connect(i,i+1,i+2.222222);
        }
        for(int i=24;i>-5;i-=5)
        {
            g.removeEdge(i,i+1);
        }
        assertEquals(g.edgeSize(),16);
        assertFalse(g.getEdge(14,15)!=null);
        g.connect(14,15,0.1);
        assertTrue(g.getEdge(15,14)!=null);
    }

    /**
     * test for node size value return
     */
    @Test
    void nodeSize() {
        directed_weighted_graph g=graph_creator(1000000,10000000,1);
        assertEquals(g.nodeSize(),1000000);
        g.addNode(new NodeData(22,0,0,0));
        g.removeNode(19);
        assertEquals(g.nodeSize(),999999);
        g.removeNode(19);
        assertEquals(g.nodeSize(),999999);
    }

    /**
     * test for edge size value return
     */
    @Test
    void edgeSize() {
        directed_weighted_graph g=graph_creator(20,5,1);
        assertEquals(g.edgeSize(),5);
        g.addNode(new NodeData(22,0,1,2));
        g.connect(19,22,24.4);
        assertEquals(6,g.edgeSize());
        g.removeEdge(19,22);
        g.removeEdge(19,22);
        assertFalse(g.getEdge(19,22)!=null);
        assertEquals(5,g.edgeSize());
    }

    /**
     * test for counter changes value return
     */
    @Test
    void getMC() {
        directed_weighted_graph g=new DWGraph_DS();
        for (int i=0;i<5;i++)
        {
            g.addNode(new NodeData(i,1,i,5));
        }
        assertEquals(5,g.getMC());
        g.removeEdge(0,1);
        g.removeNode(0);
        assertEquals(6,g.getMC());
        g.connect(0,1,2);
        g.addNode(new NodeData(0,1,1,1));
        g.connect(0,1,2);
        assertEquals(8,g.getMC());

    }

    /**
     * Generate a random graph with v_size nodes and e_size edges
     * @param v_size
     * @param e_size
     * @param seed
     * @return
     */
    public static directed_weighted_graph graph_creator(int v_size, int e_size, int seed) {
        directed_weighted_graph g = new DWGraph_DS();
        random = new Random(seed);
        for(int i=0;i<v_size;i++) {
            g.addNode(new NodeData(i,i,1,2));
        }
        int[] nodes = nodes(g);
        while(g.edgeSize() < e_size) {
            int a = nextRnd(0,v_size);
            int b = nextRnd(0,v_size);
            int i = nodes[a];
            int j = nodes[b];
            double w = random.nextDouble();
            g.connect(i,j, w);
        }
        return g;
    }
    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0+min, (double)max);
        int ans = (int)v;
        return ans;
    }
    private static double nextRnd(double min, double max) {
        double d = random.nextDouble();
        double dx = max-min;
        double ans = d*dx+min;
        return ans;
    }
    /**
     * Simple method for returning an array with all the node_data of the graph,
     * Note: this should be using an Iterator<node_edge> to be fixed in Ex1
     * @param g
     * @return
     */
    private static int[] nodes( directed_weighted_graph g) {
        int size = g.nodeSize();
        Collection<node_data> V = g.getV();
        node_data[] nodes = new node_data[size];
        V.toArray(nodes); // O(n) operation
        int[] ans = new int[size];
        for(int i=0;i<size;i++) {ans[i] = nodes[i].getKey();}
        Arrays.sort(ans);
        return ans;
    }

}
package ex2.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class DWGraph_DS implements directed_weighted_graph
{
    private HashMap<Integer,node_data> graph;
    private HashMap<Integer,EdgeList > ni;
    private int edgeSize;
    private int MC;

    public DWGraph_DS()
    {
        graph=new HashMap<Integer,node_data>();
        ni=new HashMap<Integer,EdgeList>();
    }

    public DWGraph_DS(directed_weighted_graph g)
    {
        graph = new HashMap<Integer, node_data>();
        ni = new HashMap<Integer, EdgeList>();
        for (node_data n : g.getV()) {

            addNode(new NodeData(n));
        }
        for (node_data n : g.getV()) {
            for (edge_data k : g.getE(n.getKey())) {
                connect(n.getKey(),k.getDest(),k.getWeight());
            }
        }
    }

    @Override
    public node_data getNode(int key) {
        if(graph.containsKey(key))
        {
            return graph.get(key);
        }
        return null;
    }

    @Override
    public edge_data getEdge(int src, int dest) {
        if(graph.containsKey(src) && graph.containsKey(dest))
        {
            return ni.get(src).getE(dest);
        }
        return null;
    }

    @Override
    public void addNode(node_data n) {
        if(n!=null)
        {
            graph.put(n.getKey(),n);
            ni.put(n.getKey(),new EdgeList());
            MC++;
        }
    }

    @Override
    public void connect(int src, int dest, double w) {
        if(graph.containsKey(src) && graph.containsKey(dest))
        {
            if (w>0)
            {
                edge_data e = new EdgeData(src, dest, w);
                ni.get(src).addNi(e);
                edgeSize++;
                MC++;
            }
        }
    }

    @Override
    public Collection<node_data> getV() {
        return graph.values();
    }

    @Override
    public Collection<edge_data> getE(int node_id) {
        if(graph.containsKey(node_id))
        {
            return ni.get(node_id).getV();
        }
        Collection <edge_data> c=new ArrayList<>();
        return c;
    }

    @Override
    public node_data removeNode(int key) {
        if(graph.containsKey(key))
        {
            for(node_data n:getV())
            {
                if(ni.get(n).hasE(key))
                {
                    ni.get(n).removeE(key);
                    edgeSize--;
                    MC++;
                }
            }
            node_data n=graph.remove(key);
            MC++;
            return n;
        }
        return null;
    }

    @Override
    public edge_data removeEdge(int src, int dest) {
        if(graph.containsKey(src) && graph.containsKey(dest))
        {
            if(ni.get(src).hasE(dest))
            {
                ni.get(src).removeE(dest);
                edgeSize--;
                MC++;
            }
        }
        return null;
    }

    @Override
    public int nodeSize() {
        return graph.size();
    }

    @Override
    public int edgeSize() {
        return edgeSize;
    }

    @Override
    public int getMC() {
        return MC;
    }
}

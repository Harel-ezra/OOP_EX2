package ex2.api;

import java.util.List;

public class DWGrpah_Algo implements dw_graph_algorithms{
    private directed_weighted_graph graph;

    public DWGrpah_Algo()
    {
        graph=new DWGraph_DS();
    }

    @Override
    public void init(directed_weighted_graph g) {
        if(g!=null)
        {
            this.graph=g;
        }
    }

    @Override
    public directed_weighted_graph getGraph() {
        return graph;
    }

    @Override
    public directed_weighted_graph copy() {
        return new DWGraph_DS(graph);
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public double shortestPathDist(int src, int dest) {

        return 0;
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }


}

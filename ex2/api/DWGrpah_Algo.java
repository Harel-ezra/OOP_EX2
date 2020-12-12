package ex2.api;

import java.util.*;

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
        if (graph.nodeSize() <= 1) {
            return true;
        }
        node_data n = graph.getV().iterator().next();
        HashMap<Integer, Weight> temp = Dijkstra(n.getKey());
        clear();
        if (temp.size() != graph.nodeSize())
        {
            return false;
        }
        directed_weighted_graph gRevers=reverseGraph(graph);
        n = gRevers.getV().iterator().next();
        temp = Dijkstra(n.getKey());
        clear();
        if (temp.size() == gRevers.nodeSize())
        {
            return true;
        }
        return false;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        if (graph.getNode(src) != null && graph.getNode(dest) != null) {
            HashMap<Integer, Weight> hash = Dijkstra(src);
            double path = hash.get(dest).getW();
            clear();
            return path;
        }
        return -1;
    }


    @Override
    public List<node_data> shortestPath(int src, int dest) {
        List<node_data> path = new LinkedList<node_data>();
        if (graph.getNode(src) != null && graph.getNode(dest) != null) {
            HashMap<Integer, Weight> tempH = Dijkstra(src);
            int i = dest;
            path.add(0, graph.getNode(dest));
            while (i != src) {
                Weight k = tempH.get(i); // get the father of the currently node
                path.add(0, graph.getNode(k.faterKey));
                i = k.getFaterKey();
            }
            clear();
        }
        return path;

    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }

    private HashMap<Integer, Weight> Dijkstra(int src) {
        Queue<Weight> queue = new PriorityQueue<Weight>();
        HashMap<Integer, Weight> hash = new HashMap<Integer, Weight>(); // hash for father key.
        hash.put(src, null);
        Weight w=new Weight(src,0,-1);
        queue.add(w);

        while (!queue.isEmpty()) {
            Weight n = queue.remove();
            if(graph.getNode(n.getKey()).equals("x"))
            {
                for (edge_data i : graph.getE(n.getKey()))
                {
                    if (graph.getNode(i.getDest()).getInfo().equals("x"))
                    {
                        double weight = n.getW() + i.getWeight();
                        if (i.getWeight() < 0 || weight < i.getWeight())
                        {
                            Weight e=new Weight(i.getDest(),weight, n.key);
                            queue.add(e);
                            hash.put(i.getDest(), e);
                        }
                    }
                }
                graph.getNode(n.getKey()).setInfo("v");
            }
        }
        return hash;
    }


    private void clear()
    {
        for (node_data n:graph.getV())
        {
            n.setInfo("x");
            n.setTag(-1);
        }
    }
    private directed_weighted_graph reverseGraph(directed_weighted_graph g)
    {
        directed_weighted_graph reverseG=new DWGraph_DS();
        for(node_data n:g.getV())
        {
            reverseG.addNode(new NodeData(n));
        }
        for (node_data n : g.getV()) {
            for (edge_data k : g.getE(n.getKey())) {
                reverseG.connect(k.getDest(),n.getKey(),k.getWeight());
            }
        }
        return reverseG;
    }

    private class Weight implements Comparable<Weight>
    {
        private int key;
        private double w;
        private int faterKey;
        public Weight(int key1, double w1, int key2)
        {
            this.key=key1;
            this.w=w1;
            this.faterKey=key2;
        }

        public int getKey()
        {
            return this.key;
        }

        public void setW(double weight ){
            if (weight > 0) {
                this.w = weight;
            }
        }
        public double getW()
        {
            return this.w;
        }
        public void setFaterKey(int key)
        {
            this.faterKey=key;
        }
        public int getFaterKey()
        {
            return this.faterKey;
        }

        /**
         * Comparator for compare between the w-distance for node, used for Dijkstra algoritem on Priority Queue
         */
        @Override
        public int compareTo(Weight n) {
            if (this.w > n.getW()) {
                return 1;
            }
            else if (this.w == n.getW()) {
                return 0;
            }
            return -1;
        }
    }



}

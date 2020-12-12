package ex2.api;

public class EdgeData implements edge_data {

    private int s;
    private int d;
    private double w;
    private String info="x";
    private int tag=-1;

    public EdgeData(int src, int dest, double weight) {
        this.s=src;
        this.d=dest;
        this.w=weight;
    }

    public EdgeData(edge_data k) {
        this.s=k.getSrc();
        this.d=k.getDest();
        this.w=k.getWeight();
    }

    @Override
    public int getSrc() {
        return this.s;
    }

    @Override
    public int getDest() {
        return this.d;
    }

    @Override
    public double getWeight() {
        return this.w;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        if(s!=null)
        {
            this.info=s;
        }
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag=t;
    }
}

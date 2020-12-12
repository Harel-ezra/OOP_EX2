package ex2.api;

import java.util.HashMap;

public class NodeData implements node_data{
    private int counter=0;
    private int keyId;
    private int tag=-1;
    private String info="x";
    private geo_location g;

    public NodeData(int x, int y, int z)
    {
        this.keyId=this.counter;
        counter++;
        this.g=new GeoLocation(x,y,z);
    }
    public NodeData(node_data n)
    {
        this.keyId=counter;
        counter++;
        this.tag=n.getTag();
        this.info=n.getInfo();
        this.g=new GeoLocation(n.getLocation());
    }

    @Override
    public int getKey() {
        return this.keyId;
    }

    @Override
    public geo_location getLocation() {

        return this.g;
    }

    @Override
    public void setLocation(geo_location p) {
        if(p!=null) {
            this.g = new GeoLocation(p);
        }
    }

    @Override
    public double getWeight() {
        return 0;
    }

    @Override
    public void setWeight(double w) {

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

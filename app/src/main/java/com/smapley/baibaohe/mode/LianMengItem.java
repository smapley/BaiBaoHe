package com.smapley.baibaohe.mode;

/**
 * Created by smapley on 2015/6/24.
 */
public class LianMengItem extends MainBase {
    public String nm;
    public String des;
    public String pic;
    public String dpdz;
    public String user2;
    public String cs;
    public int zb;
    public int type = 1;
    public String text;


    public void setData(String text, int type) {
        this.text = text;
        this.type = type;
    }

    @Override
    public int getType() {
        return type;
    }
}

package com.smapley.baibaohe.pubu.mode;


import com.smapley.baibaohe.pubu.library.AdapterModel;

public class Photo implements AdapterModel {

    public String user2;
    public String weight;
    public String height;
    public String pic;
    public String tm;
    public String dpicid;
    public String ming;
    public String sl;
    public String yz;
    public String picid;
    public String jilv;
    public int nw;
    public String jin;
    public String bao;
    public String jia;


    @Override
    public int getDataType() {
        int type = 1 + (int) (Math.random() * 2);
        return type;
    }
}

package com.smapley.baibaohe.mode;

/**
 * Created by smapley on 2015/6/25.
 */
public class SouSuoHead extends MainBase {
    public SouSuoHead(String text) {
        this.text = text;
    }

    public String text;
    public int Type = 0;

    @Override
    public int getType() {
        return Type;
    }
}

package com.smapley.baibaohe.mode;

import java.util.List;

/**
 * Created by smapley on 2015/6/25.
 */
public class MainHead extends MainBase{
    public List<MainItem> list;
    public int Type = 0;

    @Override
    public int getType() {
        return Type;
    }
}

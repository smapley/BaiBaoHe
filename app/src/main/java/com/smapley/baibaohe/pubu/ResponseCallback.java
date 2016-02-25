package com.smapley.baibaohe.pubu;

import com.smapley.baibaohe.pubu.mode.Photo;

import java.util.List;

public interface ResponseCallback {
    public void onSuccess(List<Photo> list);

    public void onError(int msg);
}

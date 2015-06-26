package com.longma.jack.coldweather.util;

/**
 * Created by jack on 2015/6/18.
 */
public interface HttpCallbackListener
{
    void onFinish(String response);
    void onError(Exception e);
}

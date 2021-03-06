package com.zxwl.homestart.Utlis;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by sks on 2018/4/13.
 */

public class okhttp {
    private OkHttpClient client;
    private okhttp(){
        client = new OkHttpClient.Builder().build();
    }
    private static okhttp utils2;
    public static  okhttp getInstance(){
        if(utils2==null)
            utils2 = new okhttp();
        return utils2;
    }

    public void sendGet(String url, Callback callback){
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
//            call.execute()：同步方法
        call.enqueue(callback);
    }
    public void sendPost(String url, RequestBody body, Callback callback){
        //设置post请求有两种方式：1、post()，2、method方法
        Request request = new Request.Builder().url(url).method("POST",body).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}

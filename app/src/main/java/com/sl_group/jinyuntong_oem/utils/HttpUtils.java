package com.sl_group.jinyuntong_oem.utils;


import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * okhttp请求
 */
public class HttpUtils {

    // 超时时间
    private static final int TIMEOUT = 20;
    //json请求
    private static final MediaType JSON = MediaType
            .parse("application/json; charset=utf-8");
    private static HttpUtils httpUtils = null;
    private OkHttpClient client;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Call call;

    private HttpUtils() {
        this.init();
    }

    public static HttpUtils getInstance() {

        if (httpUtils == null) {
            synchronized (HttpUtils.class) {
                if (httpUtils == null) {
                    httpUtils = new HttpUtils();

                }
            }
        }
        return httpUtils;
    }

    private void init() {

        // 设置超时时间
        client = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS).build();

    }

    /**
     * post请求，json数据为body
     */
    public void postJson(Activity activity, boolean isShow, String url, String param, final HttpCallback callback) {


        RequestBody body = RequestBody.create(JSON, param);
        Request request = new Request.Builder().url(url).post(body).build();
        onStart(activity,isShow,callback);
        call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException arg1) {
                onError(callback, arg1.getMessage());
                arg1.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()) {
                    onSuccess(callback, response.body().string());
                } else {
                    onError(callback, response.message());
                }
            }
        });
    }

    public void cancel() {
        if (call != null)
            call.cancel();
    }

    /**
     * post请求 map为body
     */
    public void post(final Activity activity, boolean isShow, String url, Map<String, Object> map, final HttpCallback callback) {

        // FormBody.Builder builder = unread FormBody.Builder();
        // FormBody body=unread FormBody.Builder().add("key", "value").build();

        // 创建请求的参数body

        FormBody.Builder builder = new FormBody.Builder();

        //遍历key

        if (null != map) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {

                System.out.println("Key = " + entry.getKey() + ", Value = "
                        + entry.getValue());
                builder.add(entry.getKey(), entry.getValue().toString());

            }
        }

        RequestBody body = builder.build();

        Request request = new Request.Builder().url(url).post(body).build();

        onStart(activity,isShow,callback);

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException arg1) {
                arg1.printStackTrace();
                onError(callback, arg1.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()) {
                    onSuccess(callback, response.body().string());

                } else {
                    onError(callback, response.message());
                }
            }

        });

    }

    /**
     * get请求
     */
    public void get(Activity activity, boolean isShow, String url, final HttpCallback callback) {

        Request request = new Request.Builder().url(url).build();

        onStart(activity,isShow,callback);

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                onError(callback, e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    onSuccess(callback, response.body().string());
                } else {
                    onError(callback, response.message());
                }
            }

        });

    }

    public void uploadMerchantImage(Activity activity, boolean isShow, String url, String mid, File file, final HttpCallback callback) {
        // form 表单形式上传
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (file.exists()) {
            // MediaType.parse() 里面是上传的文件类型。
            RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
            // 参数分别为， 请求key ，文件名称 ， RequestBody
            requestBody.addFormDataPart("file", file.getName(), body);
        }
        requestBody.addFormDataPart("mid", mid);
        Request request = new Request.Builder().url(url).post(requestBody.build()).build();
        onStart(activity,isShow,callback);
        // readTimeout("请求超时时间" , 时间单位);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onError(callback, e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    onSuccess(callback, response.body().string());
                } else {
                    onError(callback, response.message());
                }
            }
        });
    }
    public void uploadAgentImage(String url, String aid, File file, final HttpCallback callback) {
        // form 表单形式上传
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (file.exists()) {
            // MediaType.parse() 里面是上传的文件类型。
            RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
            // 参数分别为， 请求key ，文件名称 ， RequestBody
            requestBody.addFormDataPart("file", file.getName(), body);
        }
        requestBody.addFormDataPart("aid", aid);
        Request request = new Request.Builder().url(url).post(requestBody.build()).build();
        // readTimeout("请求超时时间" , 时间单位);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onError(callback, e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    onSuccess(callback, response.body().string());
                } else {
                    onError(callback, response.message());
                }
            }
        });
    }

    /**
     * log信息打印
     */
    private void debug(String params) {

        if (null == params) {
            Log.d("debug-okhttp", "params is null");
        } else {
            Log.d("debug-okhttp", params);
        }
    }

    private void onStart(Activity activity, boolean isShow, HttpCallback callback) {
        if (null != callback) {
            callback.onStart(activity,isShow);
        }
    }

    private void onSuccess(final HttpCallback callback, final String data) {

        debug(data);

        if (null != callback) {
            handler.post(new Runnable() {
                public void run() {
                    // 需要在主线程的操作。
                    callback.onSuccess(data);
                }
            });
        }
    }

    private void onError(final HttpCallback callback, final String msg) {
        if (null != callback) {
            handler.post(new Runnable() {
                public void run() {
                    // 需要在主线程的操作。
                    callback.onError(msg);
                }
            });
        }
    }


    /**
     * http请求回调
     *
     * @author Flyjun
     */
    public static abstract class HttpCallback {
        // 开始
        public void onStart(Activity activity, boolean isShow) {
            if (isShow){
                ProgressDialogUtils.showProgress(activity);
            }
        }

        // 成功回调
        public abstract void onSuccess(String data);

        // 失败回调
        public void onError(String msg) {
            LogUtils.e("请求失败："+msg);
            ProgressDialogUtils.dismissProgress();
            ToastUtils.showToast("网络异常");
        }
    }
}
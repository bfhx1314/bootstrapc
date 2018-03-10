package com.sshhww.common;

import io.appium.android.bootstrap.Logger;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by limengnan on 2018/3/9.
 */
public class BaseUtil {

    public static void wait(int second){
        try {
            Logger.info("等待:" + second + "秒" );
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static int getNumberRange(int min, int max){
        if(min == max){
            return min;
        }else if (min > max){
            int r = max;
            max = min;
            min = r;
        }

        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }
    public static void uploadMultiFile(String url, File file) {
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), fileBody)
                .addFormDataPart("path", "qutoutiao")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();


        final okhttp3.OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient = httpBuilder
                //设置超时
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("上传文件失败 : " + call.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response.toString());
            }
        });
    }


}




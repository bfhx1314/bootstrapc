package com.sshhww.common;

import io.appium.android.bootstrap.Logger;
import okhttp3.*;

import java.io.*;
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

    public static String returnExec(String cmd) {
        Logger.debug("cmd: " + cmd);
        Process p;
        String str = "";
        try {
            //执行命令
            p = Runtime.getRuntime().exec(cmd);
            BaseUtil.wait(2);
            //取得命令结果的输出流
            InputStream fis=p.getInputStream();
            //用一个读输出流类去读
            InputStreamReader isr=new InputStreamReader(fis,"GB2312");
            //用缓冲器读行
            BufferedReader br=new BufferedReader(isr);
            String line=null;
            //直到读完为止
            while((line=br.readLine())!=null) {
                str = str + line + ":";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static void exec(String cmd) {
        Logger.debug("cmd: " + cmd);
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isEmpty(String o){
        if(null == o || o.isEmpty()){
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String o){
        if(null != o && !o.isEmpty()){
            return true;
        }
        return false;
    }

}




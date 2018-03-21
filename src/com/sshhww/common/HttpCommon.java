package com.sshhww.common;

import io.appium.android.bootstrap.Logger;
import okhttp3.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * Created by limengnan on 2018/3/21.
 */
public class HttpCommon {

    public static String get(String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Logger.info("HttpRequest: " + url);
        Call call = okHttpClient.newCall(request);
        String res = "";
        try {
            Response response = call.execute();
            res=response.body().string();
            Logger.info("HttpResponse: " + res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
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


    public static void download(String url, String saveFile){

        final okhttp3.OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient = httpBuilder
                //设置超时
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
        Request request=new Request.Builder().url(url).build();


        Call call = okHttpClient.newCall(request);
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            Response response = call.execute();
            byte[] buf=new byte[2048];
            int len = 0;
            //储存下载文件的目录
            String savePath=BaseUtil.isExistDir(new File(saveFile).getParent());
            is=response.body().byteStream();
            File file=new File(savePath,new File(saveFile).getName());
            fos=new FileOutputStream(file);
            while((len = is.read(buf))!=-1){
                fos.write(buf,0,len);
            }
            fos.flush();
            Logger.info("更新文件下载完成");
        }catch (Exception e){

        }finally{
            try{
                if(is!=null)
                    is.close();
            }catch (IOException e){

            }
            try {
                if(fos!=null){
                    fos.close();
                }
            }catch (IOException e){

            }
        }
    }

}

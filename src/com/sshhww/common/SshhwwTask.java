package com.sshhww.common;

import android.os.Build;
import com.sshhww.common.bean.TaskRecordVo;
import io.appium.android.bootstrap.Logger;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by limengnan on 2018/3/9.
 */
public class SshhwwTask {

    private final static String baseParameter = "type=appium&uuid="+ Build.SERIAL+"&deviceName=" + Build.MODEL;

    private final static String taskUrl = "http://120.26.205.248:8080/update/wxcm/task.do?" + baseParameter;
    private final static String taskDoneUrl = "http://120.26.205.248:8080/update/wxcm/taskDone.do?" + baseParameter;


    public static TaskRecordVo getTask() {
        TaskRecordVo taskRecordVo = new TaskRecordVo();
        try {
            JSONObject jsonObject = new JSONObject(get(taskUrl));
            Logger.info("JSON : " + jsonObject.toString());
            taskRecordVo.setStatus(jsonObject.getString("status"));
            taskRecordVo.setDetail(jsonObject.getString("detail"));
            taskRecordVo.setTaskRecordId(jsonObject.getInt("taskRecordId"));
            taskRecordVo.setTaskRecordName(jsonObject.getString("taskRecordName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return taskRecordVo;
    }

    public static void taskDone(int taskId){
        get(taskDoneUrl + taskId) ;
    }




    private static String get(String url) {
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
}



package com.sshhww.common;

import android.os.Build;
import com.sshhww.common.bean.TaskRecordVo;
import com.sshhww.common.bean.WXCMUpdateVO;
import io.appium.android.bootstrap.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by limengnan on 2018/3/9.
 */
public class SshhwwTask {

    private final static String baseParameter = "type=appium&uuid="+ Build.SERIAL+"&deviceName=" + Build.MODEL;

    private final static String taskUrl = "http://120.26.205.248:8080/update/wxcm/task.do?" + baseParameter;
    private final static String taskDoneUrl = "http://120.26.205.248:8080/update/wxcm/taskDone.do?" + baseParameter + "&taskRecordId=";

    private final static String jarUpdateUrl = "http://120.26.205.248:8080/update/wxcm/update.do?version=0&type=jar&md5=";


    public final static String UPLOCK_APK_URL = "http://120.26.205.248:8080/apk/sshhww/unlock_apk-debug.apk";
    public final static String SETTING_APK_URL = "http://120.26.205.248:8080/apk/sshhww/settings_apk-debug.apk";
    public final static String UNICODEIME_APK_URL = "http://120.26.205.248:8080/apk/sshhww/UnicodeIME-debug.apk";

    public final static String UNLOCK_APK_PATH = "/data/local/tmp/unlock_apk-debug.apk";
    public final static String SETTINGS_APK_PATH = "/data/local/tmp/settings_apk-debug.apk";
    public final static String UNICODEIME_APK_PATH = "/data/local/tmp/UnicodeIME-debug.apk";

    private final static String sshhwwstrapPath = "/data/local/tmp/sshhwwstrap.jar";
    private final static String sshhwwstrapPathTemp = "/data/local/tmp/sshhwwstrap_temp.jar";

    public static TaskRecordVo getTask() {
        TaskRecordVo taskRecordVo = new TaskRecordVo();
        try {
            JSONObject jsonObject = new JSONObject(HttpCommon.get(taskUrl));
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
        HttpCommon.get(taskDoneUrl + taskId + "&result=1") ;
    }

    public static boolean getUpdate(){
        WXCMUpdateVO wxcmUpdateVO = new WXCMUpdateVO();
        String md5 = BaseUtil.md5(new File(sshhwwstrapPath));
        try {
            JSONObject jsonObject = new JSONObject(HttpCommon.get(jarUpdateUrl + md5));
            wxcmUpdateVO.setUpdateFilePath(jsonObject.getString("updateFilePath"));
            wxcmUpdateVO.setMd5(jsonObject.getString("md5"));
            wxcmUpdateVO.setUpdate(jsonObject.getString("update"));
        }catch (JSONException e){
            e.printStackTrace();
        }

        Logger.debug("当前strap文件MD5:" + md5);

        boolean update = false;
        if(wxcmUpdateVO.getUpdate().equalsIgnoreCase("Y")){
            update = true;
            //需要更新
            Logger.debug("服务器strap文件MD5:" + wxcmUpdateVO.getMd5());
            Logger.debug("服务器strap文件更新地址:" + wxcmUpdateVO.getUpdateFilePath());

            HttpCommon.download(wxcmUpdateVO.getUpdateFilePath(),sshhwwstrapPathTemp);

            Logger.debug("下载完strap文件MD5:" + BaseUtil.md5(new File(sshhwwstrapPathTemp)));

            if(BaseUtil.md5(new File(sshhwwstrapPathTemp)).equalsIgnoreCase(wxcmUpdateVO.getMd5())){
                Logger.info("下载strap文件MD5信息校验成功");
                BaseUtil.returnExec("cp " + sshhwwstrapPathTemp + " " + sshhwwstrapPath);
//                String pid = BaseUtil.returnExec("ps | grep uia");
//
//                ArrayList<String> pids = RegExp.matcherCharacters(pid,"[0-9]{1,}");
//                if(null == pids || pids.size() < 1){
//                    return update;
//                }
//                Logger.debug("pid:" + pids.get(0));
//                BaseUtil.rootCommand(" kill -9 " + pids.get(0));
//                //如果执行了后续步骤更新脚本失败,无法杀死自身进程
//                Logger.error("杀死自身进程失败");
            }else{
                Logger.error("下载文件MD5信息校验失败");
            }
        }
        return update;
    }


    public static void dowloadApk(String url, String path){
        HttpCommon.download(url,path);
    }



}



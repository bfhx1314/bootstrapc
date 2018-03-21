package com.sshhww.common;

import android.os.Build;
import com.sshhww.common.bean.TaskRecordVo;
import com.sshhww.common.bean.WXCMUpdateVO;
import io.appium.android.bootstrap.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;

/**
 * Created by limengnan on 2018/3/9.
 */
public class SshhwwTask {

    private final static String baseParameter = "type=appium&uuid="+ Build.SERIAL+"&deviceName=" + Build.MODEL;

    private final static String taskUrl = "http://120.26.205.248:8080/update/wxcm/task.do?" + baseParameter;
    private final static String taskDoneUrl = "http://120.26.205.248:8080/update/wxcm/taskDone.do?" + baseParameter + "&taskRecordId=";

    private final static String jarUpdateUrl = "http://120.26.205.248:8080/update/wxcm/update.do?version=0&type=jar&md5=";

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
        HttpCommon.get(taskDoneUrl + taskId) ;
    }

    public static boolean getUpdate(){
        WXCMUpdateVO wxcmUpdateVO = new WXCMUpdateVO();
        String md5 = BaseUtil.md5(new File(sshhwwstrapPath));
        try {
            JSONObject jsonObject = new JSONObject(HttpCommon.get(jarUpdateUrl + md5));
            wxcmUpdateVO.setUpdateFilePath(jsonObject.getString("updateFilePath"));
            wxcmUpdateVO.setMd5(jsonObject.getString("md5"));
        }catch (JSONException e){
            e.printStackTrace();
        }

        if(BaseUtil.isNotEmpty(wxcmUpdateVO.getUpdateFilePath()) && wxcmUpdateVO.getMd5().equalsIgnoreCase(md5)){
            //需要更新
            HttpCommon.download(wxcmUpdateVO.getUpdateFilePath(),sshhwwstrapPathTemp);

            Logger.debug("下载文件MD5:" + BaseUtil.md5(new File(sshhwwstrapPathTemp)));
            Logger.debug("服务器文件MD5:" + wxcmUpdateVO.getMd5());

            if(BaseUtil.md5(new File(sshhwwstrapPathTemp)).equalsIgnoreCase(wxcmUpdateVO.getMd5())){
                Logger.info("下载文件MD5信息校验成功");
                BaseUtil.returnExec("cp " + sshhwwstrapPathTemp + " " + sshhwwstrapPath);
                String pid = BaseUtil.returnExec("ps | grep uia");
                Logger.debug("pid:" + pid);
                Logger.debug("ls:" + BaseUtil.returnExec("ls /data"));
                BaseUtil.exec("su");
                Logger.debug("ls:" + BaseUtil.returnExec("ls /data"));

            }else{
                Logger.error("下载文件MD5信息校验失败");
                return false;
            }
        }
        return true;
    }





}



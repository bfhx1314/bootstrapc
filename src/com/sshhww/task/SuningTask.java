package com.sshhww.task;

import com.sshhww.SshhwwException;
import com.sshhww.common.BaseUtil;
import com.sshhww.driver.AndroidStrapElement;
import com.sshhww.driver.By;
import com.sshhww.driver.DragEnum;
import com.sshhww.driver.DriverCommon;
import io.appium.android.bootstrap.Bootstrap;
import io.appium.android.bootstrap.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by limengnan on 2018/3/12.
 */
public class SuningTask implements BaseTask  {

    private Bootstrap bootstrap;
    private String taskKey;

    private final String APPOINTMENT = "APPOINTMENT";
    private final String SNAP_UP = "SNAP_UP";

    private final String PACKAGENAME = "com.suning.mobile.ebuy";

    public String getAppPackage(){
        return "com.taobao.taobao";
    }

    public SuningTask(Bootstrap bootstrap, Object data){

        try {
            JSONObject jsonObject = new JSONObject(data.toString());
            taskKey = jsonObject.getString("taskKey");
        }catch (JSONException e){
            taskKey = "unknow";
            e.printStackTrace();
        }
        this.bootstrap = bootstrap;

    }


    @Override
    public void runTask()   {
        Logger.debug("当前任务:" + taskKey);
        switch (taskKey){
            case APPOINTMENT:
                appointment();
                break;
            case SNAP_UP:
                snapUp();
                break;
        }


    }


    private void appointment(){
        //请求是否已经预约成功



        common();
        BaseUtil.wait(5);
        Logger.debug("==========点击预约==========");
        DriverCommon.click(650,1200);
    }


    private void snapUp(){
        common();
        Logger.debug("==========点击抢购==========");
        DriverCommon.click(650,1200);
    }


    private void common() {
        String currentPage = bootstrap.getUiDevice().getCurrentPackageName();
        Logger.debug("当前页面:" + currentPage);
        if (currentPage.equalsIgnoreCase(PACKAGENAME)) {
            Logger.debug("关闭App");
            DriverCommon.closeApp(currentPage);
        }

        DriverCommon.startApp(PACKAGENAME + "/com.suning.mobile.ebuy.host.InitialActivity");
        BaseUtil.wait(5);

        try {

            Logger.debug("==========点击搜索框==========");
            DriverCommon.findAndroidElementAndEvent(By.id("com.suning.mobile.ebuy:id/home_a_btn_search_layout_new")).click();

            Logger.debug("==========输入搜索内容==========");
            DriverCommon.findAndroidElementAndEvent(By.id("com.suning.mobile.ebuy:id/et_search_input")).input("飞天茅台");

            Logger.debug("==========点击搜索==========");
            DriverCommon.findAndroidElementAndEvent(By.id("com.suning.mobile.ebuy:id/tv_search_input_btn")).click();

            BaseUtil.wait(5);


            Logger.debug("==========点击商品==========");
            DriverCommon.click(200, 500);


        } catch (SshhwwException e) {

            e.printStackTrace();
        }

    }
}

package com.sshhww.task;

import android.content.Context;
import android.widget.Toast;
import com.sshhww.common.BaseUtil;
import com.sshhww.common.HttpCommon;
import com.sshhww.driver.AndroidStrapElement;
import com.sshhww.driver.DragEnum;
import com.sshhww.driver.DriverCommon;
import io.appium.android.bootstrap.Bootstrap;
import io.appium.android.bootstrap.Logger;


/**
 * Created by limengnan on 2018/3/12.
 */
public class QttLookAtNewsTask implements BaseTask  {



    private final String APK_URL = "http://120.26.205.248:8080/apk/sshhww/apk/qukan.2.8.20.001.apk";
    private final String APK_PATH = "/data/local/tmp/qukan.2.8.20.001.apk";

    private final String START_ACTIVITY = "com.jifen.qukan/com.jifen.qukan.view.activity.JumpActivity";
    private Bootstrap bootstrap;

    public QttLookAtNewsTask(Bootstrap bootstrap){
        this.bootstrap = bootstrap;
    }



    public String getAppPackage(){
        return "com.jifen.qukan";
    }


    @Override
    public void runTask()   {
        if(!DriverCommon.isExistByPackageName("com.jifen.qukan")){
            if (!BaseUtil.isFileExist(APK_PATH)) {
                HttpCommon.download(APK_URL, APK_PATH);
            }
            BaseUtil.exec("chmod 777 " + APK_PATH);
            DriverCommon.installApk(APK_PATH);
        }


        //运行看新闻
        DriverCommon.startApp(START_ACTIVITY);
        Logger.debug("当前页面:" + bootstrap.getUiDevice().getCurrentPackageName());
        int lookAtNewCount = BaseUtil.getNumberRange(8,15);
        for(int i = 0; i < lookAtNewCount; i ++) {
            BaseUtil.wait(10);
            DriverCommon.drag(DragEnum.UPSLIDE.getCode(), false, false);
            BaseUtil.wait(1);
            click(DriverCommon.getAndroidStrapElementByXpath("//android.widget.TextView[@resource-id='com.jifen.qukan:id/sq']"),true);
            BaseUtil.wait(3);

            //新闻
            if(DriverCommon.getAndroidStrapElementByXpath("//android.widget.RelativeLayout[@resource-id='com.jifen.qukan:id/w']").isExist()){
                Logger.info("当前打开为新闻类型");

            //视频
            }else if(DriverCommon.getAndroidStrapElementByXpath("//android.widget.FrameLayout[@resource-id='com.jifen.qukan:id/a48']").isExist()){
                Logger.info("当前打开为视频类型");

            }else{
                DriverCommon.pressKeyCode(4);
                continue;
            }

            DriverCommon.drag(DragEnum.UPSLIDE.getCode(), false, false);

            click(DriverCommon.getAndroidStrapElementByXpath("//android.view.View[contains(@content-desc,'展开查看全文')]"),true);

            BaseUtil.wait(3);
            DriverCommon.drag(DragEnum.UPSLIDE.getCode(), false, false);
            BaseUtil.wait(3);
            DriverCommon.drag(DragEnum.UPSLIDE.getCode(), false, false);
            BaseUtil.wait(3);
            DriverCommon.drag(DragEnum.UPSLIDE.getCode(), false, false);
            BaseUtil.wait(3);
            click(DriverCommon.getAndroidStrapElementById("com.jifen.qukan:id/view_back"),true);
        }
    }
    private boolean repairStep(){
        AndroidStrapElement dnp_text_cancel = DriverCommon.getAndroidStrapElementById("com.jifen.qukan:id/n1");
        if(dnp_text_cancel.isExist()){
            Logger.info("修复方案一(新闻弹窗)元素获取成功");
            return dnp_text_cancel.click();
        }
        AndroidStrapElement avnd_img_back = DriverCommon.getAndroidStrapElementById("com.jifen.qukan:id/kq");
        if(avnd_img_back.isExist()){
            Logger.info("修复方案二(视频按钮返回)元素获取成功");
            return avnd_img_back.click();
        }
        if(DriverCommon.pressKeyCode(4)){
            Logger.info("修复方案三(模拟返回按钮事件)成功");
        }

        Logger.error("修复步骤已完成");
        if(!bootstrap.getUiDevice().getCurrentPackageName().equalsIgnoreCase(getAppPackage())){
            DriverCommon.startApp(START_ACTIVITY);
        }
        return false;
    }

    private void click(AndroidStrapElement androidStrapElement, boolean isRetry){
        if(!androidStrapElement.click()){
            Logger.error("元素点击失败 " + androidStrapElement);
            if(isRetry && repairStep()){
                androidStrapElement.click();
            }
        }
    }



}

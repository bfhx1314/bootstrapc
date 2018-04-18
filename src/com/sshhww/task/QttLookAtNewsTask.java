package com.sshhww.task;

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
        String currentPage = bootstrap.getUiDevice().getCurrentPackageName();
        Logger.debug("当前页面:" + currentPage);
        if(currentPage.equalsIgnoreCase(getAppPackage())){
            Logger.debug("关闭App");
            DriverCommon.closeApp(currentPage);
        }

        //运行看新闻
        DriverCommon.startApp(START_ACTIVITY);
        Logger.debug("当前页面:" + bootstrap.getUiDevice().getCurrentPackageName());
        int lookAtNewCount = BaseUtil.getNumberRange(8,15);
        for(int i = 0; i < lookAtNewCount; i ++) {
            BaseUtil.wait(10);
            DriverCommon.drag(DragEnum.UPSLIDE.getCode(), false, false);
            BaseUtil.wait(1);
            click(DriverCommon.getAndroidStrapElementByXpath("//android.widget.TextView[@resource-id='com.jifen.qukan:id/inew_text_title']"),true);
            BaseUtil.wait(3);


            if(DriverCommon.getAndroidStrapElementByXpath("//android.widget.RelativeLayout[@resource-id='com.jifen.qukan:id/view_back']").isExist()){
                //新闻
                Logger.info("当前打开为新闻类型");
                DriverCommon.drag(DragEnum.UPSLIDE.getCode(), false, false);
                click(DriverCommon.getAndroidStrapElementByXpath("//android.view.View[contains(@content-desc,'展开查看全文')]"),true);
                BaseUtil.wait(3);
                DriverCommon.drag(DragEnum.UPSLIDE.getCode(), false, false);
                BaseUtil.wait(3);
                DriverCommon.drag(DragEnum.UPSLIDE.getCode(), false, false);
                BaseUtil.wait(3);
                DriverCommon.drag(DragEnum.UPSLIDE.getCode(), false, false);
                BaseUtil.wait(3);
                DriverCommon.drag(DragEnum.UPSLIDE.getCode(), false, false);
                BaseUtil.wait(3);
                click(DriverCommon.getAndroidStrapElementById("com.jifen.qukan:id/view_back"),true);
            }else if(DriverCommon.getAndroidStrapElementById("com.jifen.qukan:id/avnd_img_back").isExist()){
                //视频
                Logger.info("当前打开为视频类型");
                BaseUtil.wait(25);
                click(DriverCommon.getAndroidStrapElementById("com.jifen.qukan:id/avnd_img_back"),true);
            }else{
                Logger.info("当前打开为广告类型");
                DriverCommon.pressKeyCode(4);
                continue;
            }
        }
        DriverCommon.closeApp(bootstrap.getUiDevice().getCurrentPackageName());
    }
    private boolean repairStep(){

        AndroidStrapElement dnp_text_cancel = DriverCommon.getAndroidStrapElementById("com.jifen.qukan:id/dnp_text_cancel");
        if(dnp_text_cancel.isExist()){
            Logger.info("修复方案一(新闻弹窗)元素获取成功");
            return dnp_text_cancel.click();
        }
        AndroidStrapElement avnd_img_back = DriverCommon.getAndroidStrapElementById("com.jifen.qukan:id/avnd_img_back");
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

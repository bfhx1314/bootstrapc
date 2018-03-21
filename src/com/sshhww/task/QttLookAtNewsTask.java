package com.sshhww.task;

import com.sshhww.common.BaseUtil;
import com.sshhww.driver.AndroidStrapElement;
import com.sshhww.driver.DragEnum;
import com.sshhww.driver.DriverCommon;
import io.appium.android.bootstrap.Logger;

/**
 * Created by limengnan on 2018/3/12.
 */
public class QttLookAtNewsTask implements BaseTask  {


    public String getAppPackage(){
        return "com.jifen.qukan";
    }


    @Override
    public void runTask()   {


        //运行看新闻
        DriverCommon.startApp("com.jifen.qukan/com.jifen.qukan.view.activity.JumpActivity");
        int lookAtNewCount = BaseUtil.getNumberRange(8,15);
        for(int i = 0; i < lookAtNewCount; i ++) {
            BaseUtil.wait(5);
            DriverCommon.drag(DragEnum.UPSLIDE.getCode(), false, false);

            click(DriverCommon.getAndroidStrapElementByXpath("//android.support.v7.widget.RecyclerView[@resource-id='com.jifen.qukan:id/recycler_view']/android.widget.LinearLayout[1]"),true);

            BaseUtil.wait(3);
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
            return true;
        }
        Logger.error("修复步骤失败");
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

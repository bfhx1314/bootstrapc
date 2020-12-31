package com.sshhww.task;

import com.android.uiautomator.core.UiDevice;
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
public class TaoBaoLiveTask implements BaseTask  {

    private String search;

    public String getAppPackage(){
        return "com.taobao.taobao";
    }

    public TaoBaoLiveTask(Object data){

        try {
            JSONObject jsonObject = new JSONObject(data.toString());
            search = jsonObject.getString("search");
        }catch (JSONException e){
            search = "裤子";
            e.printStackTrace();
        }catch (Exception e){
            search = "上衣";
        }
    }


    @Override
    public void runTask()   {

        String currentPage = UiDevice.getInstance().getCurrentPackageName();
        Logger.debug("当前页面:" + currentPage);
        if(currentPage.equalsIgnoreCase(getAppPackage())){
            Logger.debug("关闭App");
            DriverCommon.closeApp(currentPage);
        }

        DriverCommon.startApp("com.taobao.taobao/com.taobao.tao.welcome.Welcome");
        BaseUtil.wait(10);
        findLiveEntrance(By.xpath("//android.widget.ImageView[@content-desc='淘宝直播']"),By.xpath("//android.widget.TextView[@text='网红主播推荐']"));
        try {
            BaseUtil.wait(15);

            //随机下拉次数点击看直播
            int randomNum = BaseUtil.getNumberRange(3,6);
            while(randomNum > 0){
                DriverCommon.drag(DragEnum.UPSLIDE.getCode(),false,false);
                randomNum --;
            }
            BaseUtil.wait(5);
            DriverCommon.findAndroidElementAndEvent( By.xpath("//android.support.v7.widget.RecyclerView[@resource-id='com.taobao.taobao:id/taolive_base_list_recyclerview']/android.widget.RelativeLayout[0]")).click();

//            搜索直播间
//            DriverCommon.findAndroidElementAndEvent(By.id("com.taobao.taobao:id/taolive_menu_search")).click();
//            BaseUtil.wait(15);
//            DriverCommon.findAndroidElementAndEvent(By.id("com.taobao.taobao:id/taolive_search_edit_text")).input(search);
//            BaseUtil.wait(15);
//            DriverCommon.findAndroidElementAndEvent(By.id("com.taobao.taobao:id/taolive_search_button")).click();
            //直播间选项
//            DriverCommon.findAndroidElementAndEvent(By.id("com.taobao.taobao:id/taolive_strip_text")).click();

        }catch (SshhwwException e){
            if(e.getCode() == 10){
                DriverCommon.click(DriverCommon.getWIDTH()/2,DriverCommon.getHEIGHT()/2);
            }else{
                Logger.error(e.getMessage());
            }
        }

        int lookAtTime = BaseUtil.getNumberRange(120,600);
        Logger.debug("看直播:" + lookAtTime + "秒");
        BaseUtil.wait(lookAtTime);

        DriverCommon.closeApp(getAppPackage());
    }




    private boolean findLiveEntrance(By by,By by2){
        int i = 0;
        AndroidStrapElement androidStrapElement = new AndroidStrapElement(by);
        AndroidStrapElement androidStrapElement2 = new AndroidStrapElement(by2);

        while(!dragFindAndroidElement(androidStrapElement) && !dragFindAndroidElement(androidStrapElement2)){
            DriverCommon.drag(DragEnum.UPSLIDE.getCode(),false,false);
            if(i == 8){
                return false;
            }
            i++;
        }
        return true;
    }

    private boolean dragFindAndroidElement(AndroidStrapElement androidStrapElement){

        BaseUtil.wait(2);
        if(androidStrapElement.find()){
            androidStrapElement.click();
            return true;
        }
        return false;
    }



}

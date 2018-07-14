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
public class TaoBaoLiveTask implements BaseTask  {

    private Bootstrap bootstrap;
    private String search;

    public String getAppPackage(){
        return "com.taobao.taobao";
    }

    public TaoBaoLiveTask(Bootstrap bootstrap,Object data){

        try {
            JSONObject jsonObject = new JSONObject(data.toString());
            search = jsonObject.getString("search");
        }catch (JSONException e){
            search = "unknow";
            e.printStackTrace();
        }
        this.bootstrap = bootstrap;
    }


    @Override
    public void runTask()   {

        String currentPage = bootstrap.getUiDevice().getCurrentPackageName();
        Logger.debug("当前页面:" + currentPage);
        if(currentPage.equalsIgnoreCase(getAppPackage())){
            Logger.debug("关闭App");
            DriverCommon.closeApp(currentPage);
        }

        DriverCommon.startApp("com.taobao.taobao/com.taobao.tao.welcome.Welcome");
        BaseUtil.wait(10);
        findLiveEntrance(By.xpath("//android.widget.ImageView[@content-desc='淘宝直播']"));
        try {
            BaseUtil.wait(5);
            DriverCommon.findAndroidElementAndEvent(By.id("com.taobao.taobao:id/taolive_menu_search")).click();
            BaseUtil.wait(5);
            DriverCommon.findAndroidElementAndEvent(By.id("com.taobao.taobao:id/taolive_search_edit_text")).input(search);
            BaseUtil.wait(5);
            DriverCommon.findAndroidElementAndEvent(By.id("com.taobao.taobao:id/taolive_search_button")).click();
        }catch (SshhwwException e){
            Logger.error(e.getMessage());
        }

        DriverCommon.closeApp(getAppPackage());
    }




    private boolean findLiveEntrance(By by){
        int i = 0;
        AndroidStrapElement androidStrapElement = new AndroidStrapElement(by);
        while(!dragFindAndroidElement(androidStrapElement)){
            if(i == 8){
                return false;
            }
            i++;
        }
        return true;
    }

    private boolean dragFindAndroidElement(AndroidStrapElement androidStrapElement){
        DriverCommon.drag(DragEnum.UPSLIDE.getCode(),false,false);
        BaseUtil.wait(2);
        if(androidStrapElement.find()){
            androidStrapElement.click();
            return true;
        }
        return false;
    }



}

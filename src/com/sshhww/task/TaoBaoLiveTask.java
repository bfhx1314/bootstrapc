package com.sshhww.task;

import com.sshhww.common.BaseUtil;
import com.sshhww.driver.AndroidStrapElement;
import com.sshhww.driver.By;
import com.sshhww.driver.DragEnum;
import com.sshhww.driver.DriverCommon;

/**
 * Created by limengnan on 2018/3/12.
 */
public class TaoBaoLiveTask implements BaseTask  {


    public String getAppPackage(){
        return "com.taobao.taobao";
    }


    @Override
    public void runTask()   {
        DriverCommon.startApp("com.taobao.taobao/com.taobao.tao.welcome.Welcome");
        findLiveEntrance();
        DriverCommon.getAndroidStrapElementById("com.taobao.taobao:id/taolive_menu_search").click();
        BaseUtil.wait(4);
        DriverCommon.getAndroidStrapElementById("com.taobao.taobao:id/taolive_search_edit_text").input("男鞋");
        BaseUtil.wait(4);
        DriverCommon.getAndroidStrapElementById("com.taobao.taobao:id/taolive_search_button").click();
        BaseUtil.wait(4);


    }


    private boolean findLiveEntrance(){
        int i = 0;
        AndroidStrapElement androidStrapElement = new AndroidStrapElement(By.xpath("//android.widget.ImageView[@content-desc='淘宝直播']"));
        while(!dragFindAndroidElement(androidStrapElement)){
            if(i == 8){
                return false;
            }
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

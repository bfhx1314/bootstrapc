package com.sshhww.driver;


import com.sshhww.common.BaseUtil;
import com.sshhww.common.bean.CMD;
import com.sshhww.common.bean.DragParams;

import java.io.IOException;

public class DriverCommon {

    private static int WIDTH;
    private static int HEIGHT;

    public static int getWIDTH() {
        return WIDTH;
    }

    public static void setWIDTH(int WIDTH) {
        DriverCommon.WIDTH = WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static void setHEIGHT(int HEIGHT) {
        DriverCommon.HEIGHT = HEIGHT;
    }



    public static AndroidStrapElement getAndroidStrapElementById(String id){
        return new AndroidStrapElement(By.id(id));
    }

    public static AndroidStrapElement getAndroidStrapElementByXpath(String xpath){
        return new AndroidStrapElement(By.xpath(xpath));
    }


    private static void drag(int startX, int startY, int endX, int endY,boolean is_RuleSlide){
        DragParams dragParams = new DragParams();
        dragParams.setEndX(endX);
        dragParams.setEndY(endY);
        dragParams.setStartX(startX);
        dragParams.setStartY(startY);
        dragParams.setSteps(80);
        CMD cmd = new CMD();
        cmd.setAction("drag");
        cmd.setCmd("action");
        cmd.setParames(dragParams);
        Driver.runStep(cmd.toString());
    }


    public static void drag(String direction,boolean center, boolean is_RuleSlide){
        int startX = 0 ;
        int startY = 0;
        int endX = 0;
        int endY = 0;

        int MAX_WIDTH = WIDTH - 10;
        int MAX_HEIGHT = HEIGHT - 10;
        int MIDST_WIDTH = WIDTH/2;
        int MIDST_HEIGHT = HEIGHT/2;
        int MIN_WIDTH = 10;
        int MIN_HEIGHT = 10;

        if(center){
            MIN_WIDTH = WIDTH/4;
            MIN_HEIGHT = HEIGHT/4;
            MAX_WIDTH = WIDTH/4*3;
            MAX_HEIGHT = HEIGHT/4*3;
        }else{
            MIN_WIDTH = WIDTH/8;
            MIN_HEIGHT = HEIGHT/8;
            MAX_WIDTH = WIDTH/8*7;
            MAX_HEIGHT = HEIGHT/8*7;
        }

        if(direction.equalsIgnoreCase(DragEnum.LEFTSLIDE.getCode()) ||direction.equalsIgnoreCase("LS")){
            startX = MAX_WIDTH;
            startY = MIDST_HEIGHT;
            endX = MIN_WIDTH;
            endY = MIDST_HEIGHT;
        }else if(direction.equalsIgnoreCase(DragEnum.RIGHTSLIDE.getCode()) || direction.equalsIgnoreCase("RS")){
            startX = MIN_WIDTH;
            startY = MIDST_HEIGHT;
            endX = MAX_WIDTH;
            endY = MIDST_HEIGHT;
        }else if(direction.equalsIgnoreCase(DragEnum.UPSLIDE.getCode()) || direction.equalsIgnoreCase("US")){
            startX = MIDST_WIDTH;
            startY = MAX_HEIGHT;
            endX = MIDST_WIDTH;
            endY = MIN_HEIGHT;
        }else if(direction.equalsIgnoreCase(DragEnum.DOWNSLIDE.getCode()) || direction.equalsIgnoreCase("DS")){
            startX = MIDST_WIDTH;
            startY = MIN_HEIGHT;
            endX = MIDST_WIDTH;
            endY = MAX_HEIGHT;
        }

        drag(startX,startY,endX,endY,is_RuleSlide);

    }


    public static void startApp(String packageName){
        try {
            Runtime.getRuntime().exec(
                    "am start -W -n " + packageName);
            BaseUtil.wait(5);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeApp(String packageName){
        try {
            Runtime.getRuntime().exec(
                    "am force-stop " + packageName);
            BaseUtil.wait(5);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}



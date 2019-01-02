package com.sshhww.driver;


import com.android.uiautomator.core.UiDevice;
import com.sshhww.SshhwwException;
import com.sshhww.common.BaseUtil;
import com.sshhww.common.RegExp;
import com.sshhww.common.SystemInfo;
import com.sshhww.common.bean.CMD;
import com.sshhww.common.bean.ClickParams;
import com.sshhww.common.bean.DragParams;
import com.sshhww.common.bean.PressKeyParams;
import io.appium.android.bootstrap.AndroidCommand;
import io.appium.android.bootstrap.AndroidCommandResult;
import io.appium.android.bootstrap.CommandHandler;
import io.appium.android.bootstrap.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.json.JSONException;
import org.json.JSONObject;


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


    public static boolean pressKeyCode(int code){
        PressKeyParams p = new PressKeyParams();
        p.setKeycode(code);
        p.setMetastate(null);
        CMD cmd = new CMD();
        cmd.setAction("pressKeyCode");
        cmd.setCmd("action");
        cmd.setParames(p);
        String res = Driver.runStep(cmd.toString());
        return result(res);
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

    public static void click(int x, int y){
        ClickParams clickParams = new ClickParams();
        clickParams.setX(String.valueOf(x));
        clickParams.setY(String.valueOf(y));
        CMD cmd = new CMD();
        cmd.setAction("click");
        cmd.setCmd("action");
        cmd.setParames(clickParams);
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
        }else{
            Logger.error("无法识别滑动类型");
        }
        Logger.debug("direction:" + direction);
        Logger.debug("startX:" +startX);
        Logger.debug("startY:" +startY);
        Logger.debug("endX:" +endX);
        Logger.debug("endY:" +endY);
        drag(startX,startY,endX,endY,is_RuleSlide);

    }


    public static void startApp(String packageName){
        BaseUtil.exec("am start -W -n " + packageName);
        BaseUtil.wait(5);
    }

    public static void closeApp(String packageName){
        BaseUtil.returnExec("am force-stop " + packageName);
        int num = 10;
        while(UiDevice.getInstance().getCurrentPackageName().equalsIgnoreCase(packageName) && num > 0){
            BaseUtil.returnExec("am force-stop " + packageName);
            num --;
        }
    }

    public static boolean isExistByPackageName(String packageName){
        String res = BaseUtil.returnExec("pm list package " + packageName);
        if(res.equalsIgnoreCase("package:" + packageName + ":")){
            return true;
        }
        return false;
    }

    public static void installApk(String apkPath){
        String res = BaseUtil.returnExec("pm install " + apkPath);
        Logger.debug("Apk安装:" + res);
//        if(BaseUtil.isNotEmpty(res) && res.equalsIgnoreCase("Success")){
//            Logger.info("apk安装成功:" + apkPath);
//        }else{
//            Logger.error("apk安装失败:" + apkPath);
//        }
    }

    public static void main(String[] args) {
        String res = "\tpkg: unlock_apk-debug.apk\n" +
                "Success";
        System.out.println(res.substring(res.length()-7,res.length()));
    }


    private static boolean result(String res){
        try {
            JSONObject j = new JSONObject(res);
            if (j.getInt("status") == 0) {

                Object value = j.get("value");
                if(value instanceof  Boolean){
                    return ((Boolean) value).booleanValue();
                }else if(value.toString().equalsIgnoreCase("true")) {
                    return true;
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return false;
    }

    public static Document getPageSource(){
        CMD cmd = new CMD();
        cmd.setCmd("action");
        cmd.setAction("source");
        cmd.setParames(null);
        String res = Driver.runStep(cmd.toString());
        String value = null;
        try {
            JSONObject j = new JSONObject(res);
            if (j.getInt("status") == 0) {

                value = j.get("value").toString();
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        Document document = null;
        try {
            document = DocumentHelper.parseText(value);
        }catch (DocumentException e){
            e.printStackTrace();
        }
        return document;

    }


    public static boolean isLocked(){
        String properties = BaseUtil.returnExec(" dumpsys window");
        boolean isLocked;

        if(SystemInfo.getBrand().equalsIgnoreCase("Xiaomi:")) {
            isLocked = RegExp.findCharacters(properties, "showing=true");
            Logger.debug("isLocked:" + isLocked);
        }else {
            isLocked = RegExp.findCharacters(properties, "mShowingLockscreen=true|mDreamingLockscreen=true");
            Logger.debug("isLocked fei:" + isLocked);
        }
        return isLocked;
    }

    public static AndroidStrapElement findAndroidElementAndEvent(By by) throws SshhwwException {

        int i = 0;
        AndroidStrapElement androidStrapElement = new AndroidStrapElement(by);
        while(!androidStrapElement.find()){
            if(i>=9){
                throw new SshhwwException(10,"元素不存在:" + by.toString());
            }
            BaseUtil.wait(1);
            i++;
        }
        return androidStrapElement;

    }


}



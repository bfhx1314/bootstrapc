/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.appium.android.bootstrap;

import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.sshhww.common.BaseUtil;
import com.sshhww.common.SshhwwTask;
import com.sshhww.common.bean.TaskRecordVo;
import com.sshhww.driver.*;
import com.sshhww.task.BaseTask;
import com.sshhww.task.QttLookAtNewsTask;
import com.sshhww.task.TaobaoLiveTask;


/**
 * The Bootstrap class runs the socket server.
 */
public class Bootstrap extends UiAutomatorTestCase {


    private final String UNLOCK_APK_PATH = "/data/local/tmp/unlock_apk-debug.apk";
    private final String SETTINGS_APK_PATH = "/data/local/tmp/settings_apk-debug.apk";
    private final String UNICODEIME_APK_PATH = "/data/local/tmp/UnicodeIME-debug.apk";



    public void testRunServer() {

        Logger.info("*********开始*********");

        //初始化
        if(init()){
            //运行
            handleClientData();
        }else{
            Logger.error("脚本运行失败");
        }

        Logger.info("*********结束*********");
    }

    private boolean init(){
        DriverCommon.setHEIGHT(getUiDevice().getDisplayHeight());
        DriverCommon.setWIDTH(getUiDevice().getDisplayWidth());

        //解锁
        Logger.info("current package name: " + getUiDevice().getCurrentPackageName());
        if(getUiDevice().getCurrentPackageName().equalsIgnoreCase("com.android.keyguard")){
            Logger.info("屏幕为锁定状态,需要解锁.");
            unlock();
            if(getUiDevice().getCurrentPackageName().equalsIgnoreCase("com.android.keyguard")){
                Logger.error("屏幕解锁失败");
                return false;
            }else{
                Logger.info("屏幕解锁成功");
            }
        }

        settings();
        //支持中文
        unicode();

        return true;
    }


    public void handleClientData() {
        while(true){
            BaseUtil.wait(5);
        }
//        runTask("TaobaoLive",null);
    }

    private void runTask(){
        while(true){
            TaskRecordVo taskRecordVo = SshhwwTask.getTask();

            if(taskRecordVo.getStatus().equalsIgnoreCase("1")) {
                if(taskRecordVo.getTaskRecordId() == -1){
                    Logger.error(taskRecordVo.getDetail());
                }else{
                    runTask(taskRecordVo.getTaskRecordName(),taskRecordVo.getData());
                    SshhwwTask.taskDone(taskRecordVo.getTaskRecordId());
                }

            }else{
                Logger.error("请求异常");
            }

            BaseUtil.wait(10);
        }
    }

    private void runTask(String taskName, Object data){
        switch (taskName){
            case "QttLookAtNewsTime":
                runScript(new QttLookAtNewsTask());
                break;
            case "TaobaoLive":
                runScript(new TaobaoLiveTask());
                break;
            default:
                Logger.error("未找到定义的任务类型脚本");
                break;
        }
    }

    private void runScript(BaseTask baseTask){
//        if(getUiDevice().getCurrentPackageName().equalsIgnoreCase(baseTask.getAppPackage())){
        DriverCommon.closeApp(baseTask.getAppPackage());
        baseTask.runTask();
    }

    private void unlock(){
        if(!DriverCommon.isExistByPackageName("io.appium.unlock")){
            DriverCommon.installApk(UNLOCK_APK_PATH);
        }
        DriverCommon.startApp("io.appium.unlock/.Unlock -a android.intent.action.MAIN -c android.intent.category.LAUNCHER -f 0x10200000");
    }

    private void settings(){
        if(!DriverCommon.isExistByPackageName("io.appium.settings")){
            DriverCommon.installApk(SETTINGS_APK_PATH);
        }
        DriverCommon.startApp("io.appium.settings/.Settings -a android.intent.action.MAIN -c android.intent.category.LAUNCHER -f 0x10200000");
    }


    private void unicode(){
        if(!DriverCommon.isExistByPackageName("io.appium.android.ime")){
            DriverCommon.installApk(UNICODEIME_APK_PATH);
        }
        BaseUtil.returnExec("settings get secure default_input_method");
        BaseUtil.returnExec("ime enable io.appium.android.ime/.UnicodeIME");
        BaseUtil.returnExec("ime set io.appium.android.ime/.UnicodeIME");

    }

}

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
import com.sshhww.common.HttpCommon;
import com.sshhww.common.SshhwwTask;
import com.sshhww.common.SystemInfo;
import com.sshhww.common.bean.TaskRecordVo;
import com.sshhww.driver.*;
import com.sshhww.task.BaseTask;
import com.sshhww.task.QttLookAtNewsTask;
import com.sshhww.task.TaoBaoLiveTask;
import com.sshhww.task.TaskEnum;
import io.appium.android.bootstrap.handler.Wake;


/**
 * The Bootstrap class runs the socket server.
 */
public class Bootstrap extends UiAutomatorTestCase {






    public void testRunServer() {

        Logger.info("*********开始*********");
        Logger.setLogServer(true);
        init();
//        new TaoBaoLiveTask(this,"{\"search\":\"服装\"}").runTask();
//        //运行
        handleClientData();

        Logger.info("*********结束*********");
    }

    /**
     * 初始化运行环境
     */
    private void runInit(){
        Logger.info("current package name: " + getUiDevice().getCurrentPackageName());

        settings();
        //支持中文
        unicode();
        //解锁
        unlock();
    }

    /**
     * 初始化脚本运行环境
     * @return
     */
    private void init(){
        DriverCommon.setHEIGHT(getUiDevice().getDisplayHeight());
        DriverCommon.setWIDTH(getUiDevice().getDisplayWidth());
    }


    public void handleClientData() {
        try {
            //检查脚本更新信息
//            更新sshhwwstrap.jar 需要更新 则重新启动
            if (SshhwwTask.getUpdate()) {
                return;
            }

            //获取任务
            TaskRecordVo taskRecordVo = SshhwwTask.getTask();

            if (taskRecordVo.getStatus().equalsIgnoreCase("1")) {
                if (taskRecordVo.getTaskRecordId() == -1) {
                    Logger.error(taskRecordVo.getDetail());
                } else {
                    runTask(taskRecordVo.getTaskRecordName(), taskRecordVo.getData());
                    SshhwwTask.taskDone(taskRecordVo.getTaskRecordId());
                }

            } else {
                Logger.error("请求异常");
            }
        }catch (Exception e){
            Logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private void runTask(String taskName, Object data){
        runInit();
        switch (taskName){
            case TaskEnum.QTTLOOKATNEWSTIME:
                runScript(new QttLookAtNewsTask(this));
                break;
            case TaskEnum.TAOBAOLIVE:
                runScript(new TaoBaoLiveTask(this,data));
                break;
            default:
                Logger.error("未找到定义的任务类型脚本");
                break;
        }
    }

    private void runScript(BaseTask baseTask){
        DriverCommon.closeApp(baseTask.getAppPackage());
        baseTask.runTask();
    }

    private void unlock(){

        if(!DriverCommon.isExistByPackageName("io.appium.unlock")){
            if(!BaseUtil.isFileExist(SshhwwTask.UNLOCK_APK_PATH)) {
                HttpCommon.download(SshhwwTask.UPLOCK_APK_URL,SshhwwTask.UNLOCK_APK_PATH);
            }
            BaseUtil.returnExec("chmod 777 " + SshhwwTask.UNLOCK_APK_PATH);
            DriverCommon.installApk(SshhwwTask.UNLOCK_APK_PATH);
        }
        int i = 5;
        while(DriverCommon.isLocked()) {
            Logger.info("屏幕锁定状态");
            if(i<=0){
                Logger.error("屏幕解锁失败");
                return;
            }
            BaseUtil.exec("am start -W -n io.appium.unlock/.Unlock -a android.intent.action.MAIN -c android.intent.category.LAUNCHER -f 0x10200000");
            BaseUtil.wait(2);
            if(SystemInfo.getBrand().equalsIgnoreCase("Xiaomi:")){
                //亮屏
//            new Wake().execute(null);
                //滑动解锁
                DriverCommon.drag(DragEnum.UPSLIDE.getCode(), false, false);
            }
            i--;
        }

    }

    private void settings(){
        if(!DriverCommon.isExistByPackageName("io.appium.settings")){
            if (!BaseUtil.isFileExist(SshhwwTask.SETTINGS_APK_PATH)) {
                HttpCommon.download(SshhwwTask.SETTING_APK_URL, SshhwwTask.SETTINGS_APK_PATH);
            }
            BaseUtil.returnExec("chmod 777 " + SshhwwTask.SETTINGS_APK_PATH);
            DriverCommon.installApk(SshhwwTask.SETTINGS_APK_PATH);
        }
        DriverCommon.startApp("io.appium.settings/.Settings -a android.intent.action.MAIN -c android.intent.category.LAUNCHER -f 0x10200000");
    }


    private void unicode(){
        if(!DriverCommon.isExistByPackageName("io.appium.android.ime")){
            if (!BaseUtil.isFileExist(SshhwwTask.UNICODEIME_APK_PATH)) {
                HttpCommon.download(SshhwwTask.UNICODEIME_APK_URL, SshhwwTask.UNICODEIME_APK_PATH);
            }
            BaseUtil.returnExec("chmod 777 " + SshhwwTask.UNICODEIME_APK_PATH);
            DriverCommon.installApk(SshhwwTask.UNICODEIME_APK_PATH);
        }
        BaseUtil.returnExec("settings get secure default_input_method");
        BaseUtil.returnExec("ime enable io.appium.android.ime/.UnicodeIME");
        BaseUtil.returnExec("ime set io.appium.android.ime/.UnicodeIME");
    }


}

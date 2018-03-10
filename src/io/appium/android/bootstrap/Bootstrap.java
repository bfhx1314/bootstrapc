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


/**
 * The Bootstrap class runs the socket server.
 */
public class Bootstrap extends UiAutomatorTestCase {


    DriverCommon driverCommon = new DriverCommon();

    public void testRunServer() {

        Logger.info("*********开始*********");
        DriverCommon.setHEIGHT(getUiDevice().getDisplayHeight());
        DriverCommon.setWIDTH(getUiDevice().getDisplayWidth());

//        Logger.info("currentPackage: " + getUiDevice().getCurrentPackageName());
        handleClientData();
        Logger.info("*********结束*********");
    }



    public void handleClientData() {
        runTask();
    }

    private void runTask(){
        while(true){
            TaskRecordVo taskRecordVo = SshhwwTask.getTask();

            if(taskRecordVo.getStatus().equalsIgnoreCase("1")) {
                if(taskRecordVo.getTaskRecordId() == -1){
                    Logger.error(taskRecordVo.getDetail());
                }else{
                    runTask(taskRecordVo.getTaskRecordName());
                    SshhwwTask.taskDone(taskRecordVo.getTaskRecordId());
                }

            }else{
                Logger.error("请求异常");
            }

            BaseUtil.wait(10);
        }
    }

    private void runTask(String taskName){
        if (taskName.equalsIgnoreCase("QttLookAtNewsTime")) {

            if(getUiDevice().getCurrentPackageName().equalsIgnoreCase("com.jifen.qukan")){
                DriverCommon.closeApp("com.jifen.qukan");
            }

            //运行看新闻
            DriverCommon.startApp("com.jifen.qukan/com.jifen.qukan.view.activity.JumpActivity");
            int lookAtNewCount = BaseUtil.getNumberRange(8,15);
            for(int i = 0; i < lookAtNewCount; i ++) {
                BaseUtil.wait(3);
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

    }

    private boolean repairStep(){
        AndroidStrapElement dnp_text_cancel = DriverCommon.getAndroidStrapElementById("com.jifen.qukan:id/dnp_text_cancel");
        if(dnp_text_cancel.isExist()){
            return dnp_text_cancel.click();
        }
        AndroidStrapElement avnd_img_back = DriverCommon.getAndroidStrapElementById("com.jifen.qukan:id/avnd_img_back");
        if(avnd_img_back.isExist()){
            return avnd_img_back.click();
        }
        return false;
    }

    private void click(AndroidStrapElement androidStrapElement, boolean isRetry){
        if(!androidStrapElement.click()){
            if(isRetry && repairStep()){
                androidStrapElement.click();
            }
        }
    }




}

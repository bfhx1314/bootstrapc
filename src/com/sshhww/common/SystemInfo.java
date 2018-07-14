package com.sshhww.common;

import io.appium.android.bootstrap.Logger;

public class SystemInfo {

    public static String getBrand(){
        String info = BaseUtil.returnExec("getprop ro.product.brand");
        Logger.debug("info:[" + info + "]");
        return info;
    }

}

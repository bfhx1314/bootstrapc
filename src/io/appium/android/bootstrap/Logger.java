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

import javafx.scene.input.DataFormat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Log to standard out so that the Appium framework can pick it up.
 *
 */
public class Logger {

  private static String prefix = "[APPIUM-UIAUTO]";
  private static String suffix = "[/APPIUM-UIAUTO]";

  private final static String INFO = "info";
  private final static String DEBUG = "debug";
  private final static String ERROR = "error";

  private final static String logPath = "/data/local/tmp/appium/";

  private static boolean logServer = false;

  private static boolean isLogServer() {
    return logServer;
  }

  public static void setLogServer(boolean logServer) {
    Logger.logServer = logServer;
  }

  public static void debug(final String msg) {
    log(msg,DEBUG);
  }

  public static void error(final String msg) {
    log(msg,ERROR);
  }

  public static void info(final String msg) {
    log(msg,INFO);
  }

  private static void log(String msg,String type) {
    String context = "[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(GregorianCalendar.getInstance().getTime()) + "] "
            + Logger.prefix + " [" + type + "] " + msg + Logger.suffix + "\n";
    System.out.println(context);
    if (isLogServer()) {
      writeLocalLog(context,type);
      if(type.equalsIgnoreCase(INFO) || type.equalsIgnoreCase(ERROR)){
        writeLocalLog(context,DEBUG);
      }
    }
  }
    private static void writeLocalLog(String context,String type){

    String date = new SimpleDateFormat("yyyy-MM-dd").format(GregorianCalendar.getInstance().getTime());
    String currentLogPath = logPath + "/" + date + "/" + type + ".log";
    File logFile = new File(currentLogPath);
    if(!logFile.exists()){
      File parentFile = logFile.getParentFile();
      if(!parentFile.exists()){
        parentFile.setWritable(true);
        parentFile.mkdirs();
      }
        try {
          logFile.setWritable(true);
          logFile.createNewFile();
        }catch (Exception e){
          e.printStackTrace();
        }
    }

    FileWriter writer = null;
    try {
      // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
      writer = new FileWriter(logFile, true);
      writer.write(context);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if(writer != null){
          writer.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }


}

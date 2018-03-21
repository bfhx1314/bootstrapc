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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Log to standard out so that the Appium framework can pick it up.
 *
 */
public class Logger {

  private static String prefix = "[APPIUM-UIAUTO]";
  private static String suffix = "[/APPIUM-UIAUTO]";

  private final static String logPath = "/data/local/tmp/appium/info.log";


  public static void debug(final String msg) {
    System.out.println(Logger.prefix + " [debug] " + msg + Logger.suffix);
    log(Logger.prefix + " [debug] " + msg + Logger.suffix);
  }

  public static void error(final String msg) {
    System.out.println(Logger.prefix + " [error] " + msg + Logger.suffix);
    log(Logger.prefix + " [error] " + msg + Logger.suffix);
  }

  public static void info(final String msg) {
    System.out.println(Logger.prefix + " [info] " + msg + Logger.suffix);
    log(Logger.prefix + " [info] " + msg + Logger.suffix);

  }

  private static boolean isFirst = true;

  private static void log(String content) {
//    TODO 无法创建文件
//    if(isFirst){
//      File file = new File(logPath);
//      if(!file.exists()){
//        try {
//          file.createNewFile();
//        }catch (Exception e){
//          e.printStackTrace();
//        }
//      }
//      isFirst = false;
//    }
//
//    FileWriter writer = null;
//    try {
//      // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
//      writer = new FileWriter(logPath, true);
//      writer.write(content);
//    } catch (IOException e) {
//      e.printStackTrace();
//    } finally {
//      try {
//        if(writer != null){
//          writer.close();
//        }
//      } catch (IOException e) {
//        e.printStackTrace();
//      }
//    }
  }


}

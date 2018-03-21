package com.sshhww.common;

import io.appium.android.bootstrap.Logger;

import java.io.*;
import java.security.MessageDigest;
import java.util.Random;

/**
 * Created by limengnan on 2018/3/9.
 */
public class BaseUtil {

    public static void wait(int second){
        try {
            Logger.info("等待:" + second + "秒" );
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static int getNumberRange(int min, int max){
        if(min == max){
            return min;
        }else if (min > max){
            int r = max;
            max = min;
            min = r;
        }

        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }


    public static String returnExec(String cmd) {
        Logger.debug("cmd: " + cmd);
        Process p;
        String str = "";
        try {
            //执行命令
            p = Runtime.getRuntime().exec(cmd);
            BaseUtil.wait(2);
            //取得命令结果的输出流
            InputStream fis=p.getInputStream();
            //用一个读输出流类去读
            InputStreamReader isr=new InputStreamReader(fis,"GB2312");
            //用缓冲器读行
            BufferedReader br=new BufferedReader(isr);
            String line=null;
            //直到读完为止
            while((line=br.readLine())!=null) {
                str = str + line + ":";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static void exec(String cmd) {
        Logger.debug("cmd: " + cmd);
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isEmpty(String o){
        if(null == o || o.isEmpty()){
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String o){
        if(null != o && !o.isEmpty()){
            return true;
        }
        return false;
    }

    // 计算文件的 MD5 值
    public static String md5(File file) {
        if (file == null || !file.isFile() || !file.exists()) {
            return "";
        }
        FileInputStream in = null;
        String result = "";
        byte buffer[] = new byte[8192];
        int len;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer)) != -1) {
                md5.update(buffer, 0, len);
            }
            byte[] bytes = md5.digest();

            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(null!=in){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static String isExistDir(String saveDir) throws IOException {
        File downloadFile=new File(saveDir);
        if(!downloadFile.mkdirs()){
            downloadFile.createNewFile();
        }
        String savePath=downloadFile.getAbsolutePath();
        return savePath;
    }

}




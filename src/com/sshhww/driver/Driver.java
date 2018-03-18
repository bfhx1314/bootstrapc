package com.sshhww.driver;

import io.appium.android.bootstrap.*;
import io.appium.android.bootstrap.exceptions.CommandTypeException;
import org.json.JSONException;

import java.util.NoSuchElementException;

public class Driver {

    private static AndroidCommandExecutor executor = new AndroidCommandExecutor();


    public static String runStep(String step) {
        String inputString = step;
        Logger.debug("运行命令: " + inputString);
        String res;
        try {
            AndroidCommand cmd = getCommand(inputString);
            Logger.debug("命令类型: " + cmd.commandType().toString());
            res = runCommand(cmd);
            Logger.debug("执行结果: " + res);
        } catch (final CommandTypeException e) {
            res = new AndroidCommandResult(WDStatus.UNKNOWN_ERROR, e.getMessage())
                    .toString();
        } catch (final JSONException e) {
            res = new AndroidCommandResult(WDStatus.UNKNOWN_ERROR,
                    "脚本命令格式错误,无法解析Json内容").toString();
        }
        return res;
    }


    private static AndroidCommand getCommand(final String data) throws JSONException,
            CommandTypeException {
        return new AndroidCommand(data);
    }

    private static String runCommand(final AndroidCommand cmd) {
        AndroidCommandResult res;
        if (cmd.commandType() == AndroidCommandType.SHUTDOWN) {
            res = new AndroidCommandResult(WDStatus.SUCCESS, "OK, shutting down");
        } else if (cmd.commandType() == AndroidCommandType.ACTION) {
            try {
                res = executor.execute(cmd);
            } catch (final NoSuchElementException e) {
                res = new AndroidCommandResult(WDStatus.NO_SUCH_ELEMENT, e.getMessage());
            } catch (final Exception e) {
                Logger.debug("Command returned error:" + e);
                res = new AndroidCommandResult(WDStatus.UNKNOWN_ERROR, e.getMessage());
            }
        } else {
            res = new AndroidCommandResult(WDStatus.UNKNOWN_ERROR,
                    "Unknown command type, could not execute!");
        }
        return res.toString();
    }
}

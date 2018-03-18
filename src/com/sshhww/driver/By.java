package com.sshhww.driver;

import com.sshhww.common.bean.CMD;
import com.sshhww.common.bean.FindParams;

import java.io.Serializable;

public abstract class By {

    public static By id(String id){
        return new By.ById(id);
    }

    public static By xpath(String xpath){
        return new By.ByXpath(xpath);
    }

    private static class ById extends By implements Serializable {
        private static final long serialVersionUID = 5341968046120372169L;
        private final CMD cmd;

        public ById(String id) {
            FindParams params = new FindParams();
            params.setStrategy("id");
            params.setSelector(id);
            params.setContext("");
            params.setMultiple(true);
            CMD cmd = new CMD();
            cmd.setAction("find");
            cmd.setCmd("action");
            cmd.setParames(params);
            this.cmd = cmd;
        }

        public String toString(){
            return cmd.toString();
        }


    }

    private static class ByXpath extends By implements Serializable {
        private static final long serialVersionUID = 5341968046120372169L;
        private final CMD cmd;

        public ByXpath(String xpath) {
            FindParams params = new FindParams();
            params.setStrategy("xpath");
            params.setSelector(xpath);
            params.setContext("");
            params.setMultiple(true);
            CMD cmd = new CMD();
            cmd.setAction("find");
            cmd.setCmd("action");
            cmd.setParames(params);
            this.cmd = cmd;
        }

        public String toString(){
            return cmd.toString();
        }


    }

}

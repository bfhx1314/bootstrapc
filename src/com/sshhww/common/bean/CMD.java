package com.sshhww.common.bean;

public class CMD{
    String cmd;
    String action;
    Object parames;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Object getParames() {
        return parames;
    }

    public void setParames(Object parames) {
        this.parames = parames;
    }

    public String toString(){

        return "{\"cmd\":\"" + getCmd() + "\",\"action\":\"" + getAction() + "\",\"params\":"  +
                (parames == null ? "{}" :  getParames()) + "}";

    }


}

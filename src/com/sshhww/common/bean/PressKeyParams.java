package com.sshhww.common.bean;

public class PressKeyParams {
    Integer keycode;
    Integer metastate;

    public int getKeycode() {
        return keycode;
    }

    public void setKeycode(int keycode) {
        this.keycode = keycode;
    }

    public Integer getMetastate() {
        return metastate;
    }

    public void setMetastate(Integer metastate) {
        this.metastate = metastate;
    }

    public String toString(){

        String strn = "{" +
                (keycode    == null ? "" : "\"keycode\":\"" + getKeycode() + "\",") +
                ( "\"metastate\":" + getMetastate() + ",");
        return (strn.lastIndexOf(",") == strn.length() - 1 ? strn.substring(0,strn.length()-1) : strn) + "}";

    }


}


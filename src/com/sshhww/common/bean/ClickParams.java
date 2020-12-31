package com.sshhww.common.bean;

public class ClickParams {

    String x;
    String y;

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String toString(){

        String strn = "{" +
                (x    == null ? "" : "\"x\":\"" + getX() + "\",") +
                (y    == null ? "" : "\"y\":\"" + getY() + "\",") ;
        return (strn.lastIndexOf(",") == strn.length() - 1 ? strn.substring(0,strn.length()-1) : strn) + "}";


    }

}

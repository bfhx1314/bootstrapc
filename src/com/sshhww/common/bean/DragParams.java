package com.sshhww.common.bean;

public class DragParams {

    int startX;
    int startY;
    int endX;
    int endY;
    int steps;

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public String toString(){

        String strn = "{" +
                (startX    == -1 ? "" : "\"startX\":" + getStartX() + ",") +
                (startY    == -1 ? "" : "\"startY\":" + getStartY() + ",") +
                (endX     == -1 ? "" : "\"endX\":"  + getEndX() + ",") +
                (endY    == -1 ? "" : "\"endY\":"   + getEndY() + ",") +
                (steps   == -1 ? "" : "\"steps\":"+ getSteps() + ",");
        return (strn.lastIndexOf(",") == strn.length() - 1 ? strn.substring(0,strn.length()-1) : strn) + "}";

    }


}


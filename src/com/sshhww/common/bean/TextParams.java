package com.sshhww.common.bean;

public class TextParams {
    String elementId;
    String text;
    Boolean replace;
    Boolean unicodeKeyboard;


    public Boolean getUnicodeKeyboard() {
        return unicodeKeyboard;
    }

    public void setUnicodeKeyboard(Boolean unicodeKeyboard) {
        this.unicodeKeyboard = unicodeKeyboard;
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getReplace() {
        return replace;
    }

    public void setReplace(Boolean replace) {
        this.replace = replace;
    }


    public String toString(){

        String strn = "{" +
                (elementId   == null ? "" : "\"elementId\":\""+ getElementId() + "\",") +
                (text        == null ? "" : "\"text\":\""     + getText() + "\",") +
                (replace     == null ? "" : "\"replace\":\""  + getReplace() + "\",") +
                (unicodeKeyboard     == null ? "" : "\"unicodeKeyboard\":\""  + getUnicodeKeyboard() + "\",");
        return (strn.lastIndexOf(",") == strn.length() - 1 ? strn.substring(0,strn.length()-1) : strn) + "}";

    }


}


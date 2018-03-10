package com.sshhww.common.bean;

public class FindParams {
    String strategy;
    String selector;
    String context;
    Boolean multiple;


    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Boolean getMultiple() {
        return multiple;
    }

    public void setMultiple(Boolean multiple) {
        this.multiple = multiple;
    }


    public String toString(){

        String strn = "{" +
                (strategy    == null ? "" : "\"strategy\":\"" + getStrategy() + "\",") +
                (selector    == null ? "" : "\"selector\":\"" + getSelector() + "\",") +
                (context     == null ? "" : "\"context\":\""  + getContext() + "\",") +
                (multiple    == null ? "" : "\"multiple\":"   + getMultiple() + ",");
        return (strn.lastIndexOf(",") == strn.length() - 1 ? strn.substring(0,strn.length()-1) : strn) + "}";

    }


}


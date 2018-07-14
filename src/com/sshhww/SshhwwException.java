package com.sshhww;

/**
 * Created by limengnan on 2018/4/24.
 */
public class SshhwwException extends Exception {

    int code;

    public SshhwwException(int code, String message){
        super(message);
        this.code = code;
    }

    public SshhwwException(String message){
        super(message);
    }

    public int getCode(){
        return code;
    }

}

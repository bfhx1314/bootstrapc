package com.sshhww.driver;

public enum DragEnum {

    LEFTSLIDE("左滑", "LeftSlide"),
    RIGHTSLIDE("右滑", "RightSlide"),
    UPSLIDE("上滑", "UpSlide"),
    DOWNSLIDE("下滑", "DownSlide");

    private String name;
    private String code;

    private DragEnum(String name , String code){
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

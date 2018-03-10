package com.sshhww.driver;


import com.sshhww.common.bean.CMD;
import com.sshhww.common.bean.TextParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AndroidStrapElement {

    private boolean isExist = false;
    private String elementId;



    public AndroidStrapElement(By by){
        String res = Driver.runStep(by.toString());

        try {
            JSONObject j = new JSONObject(res);
            if (j.getInt("status") == 0) {
                setExist(true);
                JSONArray jsonArray = j.getJSONArray("value");
                if(jsonArray.length() > 0) {
                    setElementId(jsonArray.getJSONObject(0).getString("ELEMENT"));
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    public boolean click(){
        if(!isExist){
            return false;
        }
        TextParams params = new TextParams();
        params.setElementId(getElementId());
        CMD cmd = new CMD();
        cmd.setAction("element:click");
        cmd.setCmd("action");
        cmd.setParames(params);

        String res = Driver.runStep(cmd.toString());
        try {
            JSONObject j = new JSONObject(res);
            if (j.getInt("status") == 0 && j.getBoolean("value")) {
                return true;
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return false;

    }


    public static void main(String[] args) {
        AndroidStrapElement androidStrapElement = new AndroidStrapElement(By.id("xxx"));
    }

    public boolean isExist() {
        return isExist;
    }

    public void setExist(boolean exist) {
        isExist = exist;
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }
}

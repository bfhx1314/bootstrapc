package com.sshhww.driver;


import com.sshhww.common.bean.CMD;
import com.sshhww.common.bean.TextParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AndroidStrapElement {

    private boolean isExist = false;
    private String elementId;

    private String ele ;

    public AndroidStrapElement(By by){
        ele = by.toString();
        String res = Driver.runStep(ele);

        try {
            JSONObject j = new JSONObject(res);
            if (j.getInt("status") == 0) {

                JSONArray jsonArray = j.getJSONArray("value");
                if(jsonArray.length() > 0) {
                    setElementId(jsonArray.getJSONObject(0).getString("ELEMENT"));
                    setExist(true);
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    public boolean find(){
        String res = Driver.runStep(ele);

        try {
            JSONObject j = new JSONObject(res);
            if (j.getInt("status") == 0) {

                JSONArray jsonArray = j.getJSONArray("value");
                if(jsonArray.length() > 0) {
                    setElementId(jsonArray.getJSONObject(0).getString("ELEMENT"));
                    setExist(true);
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return isExist;
    }


    public boolean input(String value){
        if(!isExist){
            return false;
        }
        TextParams textParams = new TextParams();
        textParams.setElementId(getElementId());
        textParams.setText(value);
        textParams.setUnicodeKeyboard(true);
        textParams.setReplace(false);
        CMD cmd = new CMD();
        cmd.setAction("element:setText");
        cmd.setCmd("action");
        cmd.setParames(textParams);
        String res = Driver.runStep(cmd.toString());
        return true;
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

    public String toString(){
        return ele;
    }

    public boolean isExist() {
        return isExist;
    }

    private void setExist(boolean exist) {
        isExist = exist;
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }
}

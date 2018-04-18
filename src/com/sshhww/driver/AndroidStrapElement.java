package com.sshhww.driver;


import com.sshhww.common.BaseUtil;
import com.sshhww.common.bean.CMD;
import com.sshhww.common.bean.TextParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.LinkedList;
import java.util.List;

public class AndroidStrapElement {

    private boolean isExist = false;
    private String elementId;

    private String ele ;

    private int tryNnm = 10;

    private List<String> lists = new LinkedList<>();

    public AndroidStrapElement(By by){
        ele = by.toString();
        find();

    }

    private AndroidStrapElement(String elementId){
        setElementId(elementId);
        setExist(true);
        findIt();
    }

    public boolean find(){
        tryNnm = 10;
        return findIt();
    }

    private boolean findIt(){
        String res = Driver.runStep(ele);

        try {
            JSONObject j = new JSONObject(res);
            if (j.getInt("status") == 0) {

                JSONArray jsonArray = j.getJSONArray("value");
                if(jsonArray.length() > 0) {
                    for(int i = 0 ; i < jsonArray.length() ; i ++) {
                        lists.add(jsonArray.getJSONObject(i).getString("ELEMENT"));
                    }
                    setExist(true);
                }
            }else if(j.getInt("status") == 13){ //页面未加载完毕
                BaseUtil.wait(1);
                tryNnm --;
                if(tryNnm <= 0) {
                    find();
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return isExist;
    }

    public int size(){
        return lists.size();
    }

    public AndroidStrapElement get(int i){
        return new AndroidStrapElement(lists.get(i));
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

    private String getElementId() {
        return lists.get(0);
    }

    private void setElementId(String elementId) {
        lists.add(elementId);
    }
}

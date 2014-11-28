package com.hcjcch.educationaladministration.bean;

import java.util.ArrayList;

/**
 * Created by pangrong on 2014/11/6.
 */
public class ClassRoomItem {
    private String roomNames;
    private String jsbn;
    private ArrayList<String> sjd;

    public ClassRoomItem(){
        sjd = new ArrayList<String>();
    }

    public String getJsbn() {
        return jsbn;
    }

    public void setJsbn(String jsbn) {
        this.jsbn = jsbn;
    }

    public String getRoomNames() {
        return roomNames;
    }

    public void setRoomNames(String roomNames) {
        this.roomNames = roomNames;
    }

    public ArrayList<String> getSjd() {
        return sjd;
    }

    public void setSjd(ArrayList<String> sjd) {
        this.sjd = sjd;
    }
}

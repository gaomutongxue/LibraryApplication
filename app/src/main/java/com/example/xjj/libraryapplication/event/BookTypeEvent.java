package com.example.xjj.libraryapplication.event;

import android.util.Log;

public class BookTypeEvent {
    private int typeID;

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;
    public void putMessage(){
        Log.d("hhhahahahah",type);
    }
}

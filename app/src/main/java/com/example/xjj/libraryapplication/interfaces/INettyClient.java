package com.example.xjj.libraryapplication.interfaces;

public interface INettyClient {
    void  connect(String host,int port);
    void sendMessage(int mt,String msg,long delayed);
    void addDataReceiveListener(OnDataReceiveListener onDataReceiveListener);
    interface OnDataReceiveListener{
        void onDataReceive(int mt,String json);
    }
    interface OnconnectStatusListener{
        void onDisconnected();
    }
}

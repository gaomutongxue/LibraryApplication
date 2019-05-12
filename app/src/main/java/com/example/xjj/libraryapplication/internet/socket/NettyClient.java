//package com.example.xjj.libraryapplication.internet.socket;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.HandlerThread;
//import android.os.Message;
//import android.text.TextUtils;
//import android.util.Log;
//
//import com.example.xjj.libraryapplication.interfaces.INettyClient;
//import com.example.xjj.libraryapplication.util.LogUtils;
//import com.example.xjj.libraryapplication.util.ToastUtils;
//
//
//import java.net.InetSocketAddress;
//import java.util.concurrent.TimeUnit;
//
//import io.netty.bootstrap.Bootstrap;
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.ChannelFutureListener;
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.ChannelPipeline;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.channel.socket.nio.NioSocketChannel;
//import io.netty.handler.codec.LineBasedFrameDecoder;
//import io.netty.handler.codec.string.StringEncoder;
//import io.netty.handler.timeout.IdleStateHandler;
//import io.netty.util.CharsetUtil;
//
//public class NettyClient implements INettyClient {
//    private final String TAG = NettyClient.class.getSimpleName();
//    private static NettyClient mInstance;
//    private Bootstrap bootstrap;
//    private Channel channel;
//    private String host;
//    private int port;
//    private boolean isConnect=true;
//    private boolean isConnecting=true;
//    private ChannelFuture channelFuture = null;
//    private HandlerThread workThread = null;
//    private Handler mWorkHandler = null;
//    private MyClientHandler nettyClientHandler;
//    private final String ACTION_SEND_TYPE = "action_send_type";
//    private final String ACTION_SEND_MSG = "action_send_msg";
//    private final int MESSAGE_INIT = 0x1;
//    private final int MESSAGE_CONNECT = 0x2;
//    private final int MESSAGE_SEND = 0x3;
//    private NioEventLoopGroup group;
//    private boolean isNeedReconnect=true;
//    private Handler.Callback mWorkHandlerCallback = new Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message msg) {
//            switch (msg.what) {
//                case MESSAGE_INIT: {
//                     group= new NioEventLoopGroup();
//                    bootstrap = new Bootstrap();
//                    bootstrap.channel(NioSocketChannel.class);
//                    bootstrap.group(group);
//                    bootstrap.handler(new ChannelInitializer<SocketChannel>() {
//                        @Override protected void initChannel(SocketChannel ch) throws Exception {
//                            ChannelPipeline pipeline = ch.pipeline();
//                            pipeline.addLast(new IdleStateHandler(5,0,0, TimeUnit.SECONDS));
//                            pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
//                            pipeline.addLast(new LineBasedFrameDecoder(Integer.MAX_VALUE));
//                            pipeline.addLast(nettyClientHandler);
//                        }
//                    });
//                    break;
//                }
//                case MESSAGE_CONNECT:{
//                    try{
//                        if (TextUtils.isEmpty(host) || port == 0){
//                            Exception exception = new Exception("Netty host | port is invalid");
//                            throw exception;
//                        }
//                        channelFuture = bootstrap.connect(new InetSocketAddress(host, port)).addListener(new ChannelFutureListener() {
//                            @Override
//                            public void operationComplete(ChannelFuture channelFuture) throws Exception {
//                                if (channelFuture.isSuccess()) {
//                                    Log.e(TAG,"连接成功");
//                                    isConnect = true;
//                                    channel = channelFuture.channel();
//                                } else {
//                                    Log.e(TAG,"连接失败");
//                                    isConnect = false;
//                                }
//                                isConnecting = false;
//                            }
//                        }).sync();
//                    } catch (Exception e) {
//
//                        Log.d("aaa","连接问题");
//                        e.printStackTrace();
//                        sendReconnectMessage();
//                    }
//                    break;
//                }
//                case MESSAGE_SEND:
//                {
//                    String sendMsg = msg.getData().getString(ACTION_SEND_MSG);
//                    int mt = msg.getData().getInt(ACTION_SEND_TYPE);
//                    try {
//                        if (channel != null && channel.isOpen()){
//                            channel.writeAndFlush(constructMessage(sendMsg)).sync();
//                            Log.d(TAG, "send succeed " + constructMessage(sendMsg));
//                        } else {
//                            throw new Exception("channel is null | closed");
//                        }
//                    } catch (Exception e){
//
////                        LogUtils.e(TAG, "send failed " + e.getMessage());
//                        sendReconnectMessage();
//                        e.printStackTrace();
//                    }finally{
//
//                        if (1 == mt) sendMessage(mt, sendMsg, 1000);
//                    }
//                    break;
//                }
//            }
//            return true;
//        }
//
//
//    };
//
//
//    private NettyClient() {
//        init();
//    }
//    private void init() {
//        workThread = new HandlerThread(NettyClient.class.getName());
//        workThread.start();
//        mWorkHandler = new Handler(workThread.getLooper(), mWorkHandlerCallback);
//        nettyClientHandler = new MyClientHandler();
//        nettyClientHandler.setConnectStatusListener(new OnconnectStatusListener() {
//            @Override
//            public void onDisconnected() {
//                sendReconnectMessage();
//            } });
//        mWorkHandler.sendEmptyMessage(MESSAGE_INIT);
//
//    }
//    public synchronized static NettyClient getInstance() {
//        if (mInstance == null)
//            mInstance = new NettyClient();
//        return mInstance;
//    }
//
//
//
//    private String constructMessage(String sendMsg) {
//        String message;
//        //与后台协议好，如何设置校验部分，然后和json一起发给服务器
//        return sendMsg;
//    }
//
//    private void sendReconnectMessage() {
//        mWorkHandler.sendEmptyMessageDelayed(MESSAGE_CONNECT,2000);
//    }
//    @Override
//    public void connect(String host, int port) {
//        this.host = host;
//        this.port = port;
//        mWorkHandler.sendEmptyMessage(MESSAGE_CONNECT);
//    }
//
//    @Override
//    public void sendMessage(int mt, String msg, long delayed) {
//        if (TextUtils.isEmpty(msg))
//            return;
//        Message message = new Message();
//        Bundle bundle = new Bundle();
//        message.what = MESSAGE_SEND;
//        bundle.putString(ACTION_SEND_MSG, msg);
//        bundle.putInt(ACTION_SEND_TYPE, mt);
//        message.setData(bundle);
//        mWorkHandler.sendMessageDelayed(message, delayed);
//    }
//
//    @Override
//    public void addDataReceiveListener(OnDataReceiveListener onDataReceiveListener) {
//        if (nettyClientHandler != null)
//            nettyClientHandler.addDataReceiveListener(onDataReceiveListener);
//
//    }
//    public void disconnect() {
//        Log.e(TAG, "disconnect");
//        isNeedReconnect = false;
//        group.shutdownGracefully();
//    }
//}
///**
// * Activity调用
// */
////NettyClient.getInstance().addDataReceiveListener(new INettyClient.OnDataReceiveListener() { @Override public void onDataReceive(int mt, String json) {
//

//package com.example.xjj.libraryapplication.internet.socket;
//
//import android.os.Looper;
//import android.util.Log;
//
//import com.example.xjj.libraryapplication.interfaces.INettyClient;
//import com.google.gson.Gson;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.logging.Handler;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelInboundHandlerAdapter;
//import io.netty.handler.timeout.IdleState;
//import io.netty.handler.timeout.IdleStateEvent;
//
//public class MyClientHandler extends ChannelInboundHandlerAdapter {
//    private final String TAG="MyClientHandler";
//    private INettyClient.OnconnectStatusListener onconnectStatusListener;
//    private List<INettyClient.OnDataReceiveListener> listeners=new ArrayList<>();
//
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        //channelActive()方法将会在连接被建立并且准备进行通信时被调用。
//        Log.d(TAG, "channel active");
//        super.channelActive(ctx);
//    }
//
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        //channelRead()方法是在数据被接收的时候调用。
//        ByteBuf buf=(ByteBuf)msg;
//        byte[] bytes=new byte[buf.readableBytes()];
//        buf.readBytes(bytes);
//        String body=new String(bytes,"UTF-8");
//        //对body进行规则检验
//        body = verify(body);
//        if (body!=null){
//            parseJson(body);
//        }
//
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        //exceptionCaught()事件处理方法是当出现Throwable对象才会被调用，
//        //即当Netty由于IO错误或者处理器在处理事件时抛出的异常时。
//       //在大部分情况下，捕获的异常应该被记录下来并且把关联的channel给关闭掉。
//        ctx.close();
//       if (onconnectStatusListener!=null){
//           onconnectStatusListener.onDisconnected();
//       }
//    }
//
//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        ctx.fireChannelReadComplete();
//        super.channelReadComplete(ctx);
//    }
//
//
//    private void parseJson(String body) {
//        //TODO 解析JSON语句
////        取出请求字段
//        int msg=1;
//        callListener(1,body);
//    }
//
//    private void callListener(final int msgType, final String body) {
//        for (final INettyClient.OnDataReceiveListener listener:listeners){
//            if (listener!=null)
//                new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
//                    @Override
//                    public void run() {
//                   listener.onDataReceive(msgType,body);
//                    }
//                });
//        }
//    }
//
//
//    private String verify(String body) {
//        //TODO 协议提取数据
//        return body;
//    }
//    public void addDataReceiveListener(INettyClient.OnDataReceiveListener listener) {
//        if (!listeners.contains(listener))
//            listeners.add(listener);
//    }
//    public void setConnectStatusListener(INettyClient.OnconnectStatusListener listener) {
//        onconnectStatusListener = listener;
//    }
//
//    @Override
//    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        super.userEventTriggered(ctx, evt);
//        //收发心跳
//        if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
//
//            IdleStateEvent event = (IdleStateEvent) evt;
//
//            if (event.state() == IdleState.READER_IDLE)
//
//                System.out.println("read idle");
//
//            else if (event.state() == IdleState.WRITER_IDLE)
//
//                System.out.println("write idle");
//
//            else if (event.state() == IdleState.ALL_IDLE)
////              NettyClient.getInstance().connect();
//                System.out.println("all idle");
//
//        }
//    }
//
//    @Override
//    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
////        System.out.println("链接关闭");
////        if (reconnect) {
////            System.out.println("链接关闭，将进行重连");
////            if (attempts < 12) {
////                attempts++;
////            }           //重连的间隔时间会越来越长
////            int timeout = 2 << attempts;
////            timer.newTimeout(this, timeout, TimeUnit.MILLISECONDS);
////        }
////        ctx.fireChannelInactive();
//    }
//
//}

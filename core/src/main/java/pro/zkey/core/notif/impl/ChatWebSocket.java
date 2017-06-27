//package pro.zkey.core.notif.impl;
//
//import okhttp3.*;
//import okio.ByteString;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.concurrent.TimeUnit;
//
//public final class ChatWebSocket extends WebSocketListener {
//    @Override
//    public void onOpen(WebSocket webSocket, Response response) {
//        super.onOpen(webSocket, response);
//    }
//
//    @Override
//    public void onMessage(WebSocket webSocket,final String text) {
//        super.onMessage(webSocket, text);
////        runOnUiThread(new Runnable() {
////            @Override
////            public void run() {
////                tv.append("\n"+text);
////            }
////        });
//
//    }
//
//    @Override
//    public void onClosed(WebSocket webSocket, int code, String reason) {
//        super.onClosed(webSocket, code, reason);
//    }
//
//    @Override
//    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
//        super.onFailure(webSocket, t, response);
//    }
//}
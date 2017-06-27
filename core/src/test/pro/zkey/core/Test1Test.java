package pro.zkey.core;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Test;
import pro.zkey.core.cache.impl.CacheImpl;
import pro.zkey.core.cache.impl.CacheListDataImpl;
import pro.zkey.core.notif.impl.ServiceClientImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by ps on 2017/6/16.
 */
public class Test1Test {

    @Test
    public void 检查系统是否正常(){
//        MockWebServer mockWebServer = new MockWebServer();
//        mockWebServer.enqueue(new MockResponse().withWebSocketUpgrade(new WebSocketListener() {
//            @Override
//            public void onOpen(WebSocket webSocket, Response response) {
//                System.out.println("server onOpen");
//                System.out.println("server request header:" + response.request().headers());
//                System.out.println("server response header:" + response.headers());
//                System.out.println("server response:" + response);
//            }
//            @Override
//            public void onMessage(WebSocket webSocket, String string) {
//                System.out.println("server onMessage");
//                System.out.println("message:" + string);
//                //接受到5条信息后，关闭消息定时发送器
////                if(msgCount == 5){
////                    mTimer.cancel();
////                    webSocket.close(1000, "close by server");
////                    return;
////                }
//                webSocket.send("response-" + string);
//            }
//            @Override
//            public void onClosing(WebSocket webSocket, int code, String reason) {
//                System.out.println("server onClosing");
//                System.out.println("code:" + code + " reason:" + reason);
//            }
//            @Override
//            public void onClosed(WebSocket webSocket, int code, String reason) {
//                System.out.println("server onClosed");
//                System.out.println("code:" + code + " reason:" + reason);
//            }
//            @Override
//            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
//                //出现异常会进入此回调
//                System.out.println("server onFailure");
//                System.out.println("throwable:" + t);
//                System.out.println("response:" + response);
//            }
//        }));
//        String hostName = mockWebServer.getHostName();
//        int port = mockWebServer.getPort();
//        System.out.println("hostName:" + hostName);
//        System.out.println("port:" + port);
        ServiceClientImpl serviceClient =new ServiceClientImpl();
        serviceClient.buildLinks();
//        pro.zkey.core.cache.impl.CacheImpl test=new pro.zkey.core.cache.impl.CacheImpl();
//        CacheListDataImpl aa = test.get(CacheListDataImpl.class,"asdasd");
//        aa.getClass();
//        CacheListDataImpl aa = (CacheListDataImpl)test.get("asdasd");

//        test.get<String>("asdasd");
//        System.out.println( test.get("test"));
//        List a=new ArrayList();
//        a.add(1,"");
        assertTrue( true );
    }
}

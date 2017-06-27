package pro.zkey.core.notif.impl;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.ProxyServer;
import com.ning.http.client.ws.WebSocket;
import com.ning.http.client.ws.WebSocketListener;
import com.ning.http.client.ws.WebSocketTextListener;
import com.ning.http.client.ws.WebSocketUpgradeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Created by ps on 2017/6/26.
 */
public class ServiceClientImpl {
    // 日志
    private Logger logger = LoggerFactory.getLogger(ServiceClientImpl.class);
    public void buildLinks() {

//        ProxyServer proxyServer = new ProxyServer("localhost", 2048);
        AsyncHttpClientConfig cf =new AsyncHttpClientConfig.Builder().build();
        AsyncHttpClient c = new AsyncHttpClient(cf);
        try {
            WebSocket websocket = c.prepareGet("ws://localhost:2048/ws")
                    .execute(new WebSocketUpgradeHandler.Builder().addWebSocketListener(
                            new WebSocketTextListener() {

                                @Override
                                public void onMessage(String message) {
                                }

                                @Override
                                public void onOpen(WebSocket websocket) {
                                    websocket.sendMessage("asasdkjaskj");
                                }

                                @Override
                                public void onClose(WebSocket websocket) {
//                                latch.countDown();
                                }

                                @Override
                                public void onError(Throwable t) {
                                }
                            }).build()).get();
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}

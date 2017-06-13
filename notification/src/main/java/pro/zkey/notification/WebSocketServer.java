package pro.zkey.notification;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.ImmediateEventExecutor;

import java.io.RandomAccessFile;
import java.net.InetSocketAddress;

/**
 * Websocket服务器
 * 2017-06-13 10:39:17
 * @author PS
 */
public class WebSocketServer {
    private final ChannelGroup group = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();
    private Channel channel;

    public ChannelFuture start(InetSocketAddress address) {
        ServerBootstrap boot = new ServerBootstrap();
        boot.group(workerGroup).channel(NioServerSocketChannel.class).childHandler(createInitializer(group));

        ChannelFuture f = boot.bind(address).syncUninterruptibly();
        channel = f.channel();
        return f;
    }

    protected ChannelHandler createInitializer(ChannelGroup group2) {
        return new ChatServerInitializer(group2);
    }

    public void destroy() {
        if (channel != null)
            channel.close();
        group.close();
        workerGroup.shutdownGracefully();
    }

    public static void main(String[] args) {
        final WebSocketServer server = new WebSocketServer();
        ChannelFuture f = server.start(new InetSocketAddress(2048));
        System.out.println("server start................");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                server.destroy();
            }
        });
        f.channel().closeFuture().syncUninterruptibly();
    }

    private static WebSocketServer instance;

    private WebSocketServer() {}

    public static synchronized WebSocketServer getInstance() {// 懒汉，线程安全
        if (instance == null) {
            instance = new WebSocketServer();
        }
        return instance;
    }

    public void running(){
        if(instance != null){

            String port=null;
            port="8000";//获取端口号
            if(null==port||port.length()<0){
                port="18080";
            }
            instance.start(new InetSocketAddress(Integer.valueOf(port)));
            //ChannelFuture f =
            System.out.println("----------------------------------------WEBSOCKET SERVER START----------------------------------------");
            /*Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    instance.destroy();
                }
            });
            f.channel().closeFuture().syncUninterruptibly();*/
        }
    }
}

//初始化链：
class ChatServerInitializer extends ChannelInitializer<Channel> {

    private final ChannelGroup group;
    public ChatServerInitializer(ChannelGroup group) {
        super();
        this.group = group;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new HttpServerCodec());

        pipeline.addLast(new ChunkedWriteHandler());

        pipeline.addLast(new HttpObjectAggregator(64*1024));

        pipeline.addLast(new HttpRequestHandler("/ws"));

        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        pipeline.addLast(new TextWebSocketFrameHandler(group));

    }

}

//HTTP请求处理：
class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final String wsUri;
    public HttpRequestHandler(String wsUri) {
        super();
        this.wsUri = wsUri;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg)
            throws Exception {
        if(wsUri.equalsIgnoreCase(msg.getUri())){
            ctx.fireChannelRead(msg.retain());
        }else{
            if(HttpHeaders.is100ContinueExpected(msg)){
                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
                ctx.writeAndFlush(response);
            }

            RandomAccessFile file = new RandomAccessFile(HttpRequestHandler.class.getResource("/").getPath()+"/index.html", "r");
            HttpResponse response = new DefaultHttpResponse(msg.getProtocolVersion(), HttpResponseStatus.OK);
            response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/html;charset=UTF-8");

            boolean isKeepAlive = HttpHeaders.isKeepAlive(msg);
            if(isKeepAlive){
                response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, file.length());
                response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            }

            ctx.write(response);
            if(ctx.pipeline().get(SslHandler.class) == null){
                ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
            }else{
                ctx.write(new ChunkedNioFile(file.getChannel()));
            }

            ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
            if(isKeepAlive == false){
                future.addListener(ChannelFutureListener.CLOSE);
            }

            file.close();
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
        cause.printStackTrace(System.err);
    }
}

//websocket处理：
class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final ChannelGroup group;

    public TextWebSocketFrameHandler(ChannelGroup group) {
        super();
        this.group = group;
    }
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
            throws Exception {

        if(evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE){
            ctx.pipeline().remove(HttpRequestHandler.class);

            group.writeAndFlush(new TextWebSocketFrame("Client "+ctx.channel()+" joined!"));

            group.add(ctx.channel());
        }else{
            super.userEventTriggered(ctx, evt);
        }

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx,
                                TextWebSocketFrame msg) throws Exception {
        String request = msg.text();
        System.out.println("服务端收到：" + request);
        TextWebSocketFrame tws = new TextWebSocketFrame("测试返回1:"
                + ctx.channel().id() + "：" + request);
        group.writeAndFlush(tws);

    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
        cause.printStackTrace();
    }
}


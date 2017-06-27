package pro.zkey.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;

/**
 * Created by ps on 2017/6/16.
 */
public class Test {
    private Logger logger = LoggerFactory.getLogger(App.class);
    public String aa(){
        // 记录error信息

        logger.error("[info message]");

// 记录info，还可以传入参数

        logger.info("[info message]{},{},{},{}", "abc", false, 123,"asd");

// 记录deubg信息

        logger.debug("[debug message]");

// 记录trace信息

        logger.trace("[trace message]");

        System.out.println( "Hello World!" );
        return "啊啊";
    }

    private static JedisPool pool;

    public void testRedis(){
//        pool = new JedisPool(jpc, host, port, timeOut, password);
    }
}

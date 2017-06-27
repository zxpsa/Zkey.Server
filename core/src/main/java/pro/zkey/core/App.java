package pro.zkey.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    static private Logger logger = LoggerFactory.getLogger(App.class);
    public static void main( String[] args )
    {
        // 记录error信息

        logger.error("[info message]");

// 记录info，还可以传入参数

        logger.info("[info message]{},{},{},{}", "abc", false, 123,"asd");

// 记录deubg信息

        logger.debug("[debug message]");

// 记录trace信息

        logger.trace("[trace message]");

        System.out.println( "Hello World!" );
    }
}

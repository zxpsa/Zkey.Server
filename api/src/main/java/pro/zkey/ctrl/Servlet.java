package pro.zkey.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import pro.zkey.werwolf.demo.services.Test;
import pro.zkey.werwolf.demo.services.impl.ITest;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

/**
 * Created by ps on 2017/6/5.
 */
public class Servlet extends HttpServlet {

//    @Autowired
//    Test1 serviceTest;
        @Autowired
ITest test;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        String jsonStr = "{\"name\":\"fly\",\"type\":\"虫子\"}";
        PrintWriter out = null;
        try {
            out = response.getWriter();
            jsonStr=test.getTest();
            out.write(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public void init(ServletConfig servletConfig) throws ServletException {
        ServletContext servletContext = servletConfig.getServletContext();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext.getAutowireCapableBeanFactory();
        autowireCapableBeanFactory.configureBean(this, "ITest");
    }

}

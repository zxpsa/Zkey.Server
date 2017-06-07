package pro.zkey.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ps on 2017/6/6.
 */
@Component
public class ITest1 implements Test1{
    @Autowired
    private ServiceTest serviceTest;
    public String ceshi() throws Exception{
        String res="";
        res=serviceTest.res();

        return res;
    }
}

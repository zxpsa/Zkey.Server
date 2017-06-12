package pro.zkey.werwolf.demo.services.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pro.zkey.werwolf.demo.daos.TestDao;
import pro.zkey.werwolf.demo.entitys.TestUser;
import pro.zkey.werwolf.demo.services.Test;

import java.util.List;

/**
 * Created by ps on 2017/6/8.
 */
@Component
public class ITest implements Test{
    @Autowired
    TestDao testDao;
    @Transactional(rollbackFor=RuntimeException.class)
    public String getTest() throws Exception {
        TestUser testUser =new TestUser();
        testUser.setAa(321);
        testDao.addData(testUser);
        List<TestUser> testUsers = testDao.selectTest();
        String str="";
        for (TestUser s : testUsers) {
            str=str+"__"+s.getAa();
        }
        throw new RuntimeException("错误异常");
//        return str;
    }
}

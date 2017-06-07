package pro.zkey.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.zkey.dao.Test;
import pro.zkey.dtos.User;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ps on 2017/6/5.
 */
@Component
public class IServiceTest implements ServiceTest {
    @Autowired
    private Test Test2;
    @Transactional(rollbackFor=Exception.class)
    public String res() throws Exception {
        User u= new User();
        u.setAa(123);
        Test2.addData(u);

        List<User> a=Test2.selectTest();
        throw new Exception("aaa");
//        return ""+a.size();
    }
}

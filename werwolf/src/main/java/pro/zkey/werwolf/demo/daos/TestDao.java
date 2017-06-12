package pro.zkey.werwolf.demo.daos;

import org.springframework.stereotype.Repository;
import pro.zkey.werwolf.demo.entitys.TestUser;
import java.util.List;

@Repository
public interface TestDao {

    /**
     * 查询测试
     * @return
     */
    List<TestUser> selectTest();

    /**
     * 保存测试
     * @param user
     */
    void addData(TestUser user);
}

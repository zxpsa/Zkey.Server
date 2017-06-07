package pro.zkey.dao;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pro.zkey.dtos.User;

import java.util.List;

@Repository
public interface Test {
    List<User> selectTest();
    void addData(User user);
}

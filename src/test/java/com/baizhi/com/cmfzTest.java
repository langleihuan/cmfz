package com.baizhi.com;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.MapVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
public class cmfzTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void qureyBy(){
        Integer i = userDao.queryBy("男", "3");
            System.out.println(i);
    }

    @Test
    public void queryByArea(){
        List<MapVO> users = userDao.queryByAera();
    }

    @Test
    public void UUid(){
        String s = UUID.randomUUID().toString();
        System.out.println(s);
    }
}

package com.baizhi.service;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.MapVO;
import com.baizhi.entity.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User login(User user) {
        return userDao.login(user.getPhone(),user.getPassword());
    }

    @Override
    public User queryOne(User user) {
        return userDao.selectOne(user);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> queryByPage(Integer size, Integer page) {
        Map<String,Object> map = new HashMap<>();
        Integer count = userDao.selectCount(new User());
        Integer totalPage = 0;
        if(count%size==0){
            totalPage = count/size;
        }else{
            totalPage = count/size+1;
        }
        Integer start = (page-1)*size;
        map.put("records",count);
        map.put("page",page);
        map.put("total",totalPage);
        map.put("rows",userDao.selectByRowBounds(new User(),new RowBounds(start, size)));
        return map;
    }

    @Override
    public void addUser(User user) {
        userDao.insert(user);
    }

    @Override
    public Map add(User user) {
        HashMap hashMap = new HashMap();
        String s = UUID.randomUUID().toString();
        user.setId(s);
        user.setCreate_time(new Date());
        user.setLastlogintime(new Date("2018/12/12"));
        userDao.insert(user);
        hashMap.put("userId",s);
        hashMap.put("status",200);
        return hashMap;
    }

    @Override
    public Map updata(User user) {
        HashMap hashMap = new HashMap();
        userDao.updateByPrimaryKeySelective(user);
        return hashMap;
    }

    @Override
    public Map delete(String id) {
        HashMap hashMap = new HashMap();
        userDao.deleteByPrimaryKey(id);
        return hashMap;
    }

    @Override
    public Map queryBy(String sex, String time) {
        Map map = new HashMap();

        Integer num1 = userDao.queryBy("男", "1");
        Integer num2 = userDao.queryBy("男", "7");
        Integer num3 = userDao.queryBy("男", "30");
        Integer num4 = userDao.queryBy("男", "365");

        Integer num5 = userDao.queryBy("女", "1");
        Integer num6 = userDao.queryBy("女", "7");
        Integer num7 = userDao.queryBy("女", "30");
        Integer num8 = userDao.queryBy("女", "365");

        map.put("man1",num1);
        map.put("man7",num2);
        map.put("man30",num3);
        map.put("man365",num4);
        map.put("woman1",num5);
        map.put("woman7",num6);
        map.put("woman30",num7);
        map.put("woman365",num8);
        return map;
    }

    @Override
    public List<MapVO> queryByArea() {
        return userDao.queryByAera();
    }
}

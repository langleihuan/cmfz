package com.baizhi.service;

import com.baizhi.entity.MapVO;
import com.baizhi.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    public User login(User user);

    public User queryOne(User user);

    public Map<String,Object> queryByPage(Integer size, Integer page);

    public void addUser(User user);

    Map add(User user);

    Map updata(User user);

    Map delete(String id);

    public Map queryBy(String sex,String time);

    public List<MapVO> queryByArea();
}

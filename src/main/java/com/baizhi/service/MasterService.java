package com.baizhi.service;

import com.baizhi.entity.Master;

import java.util.Map;

public interface MasterService {
    public Map<String,Object> queryByPage(Integer size, Integer page);

    Map add(Master master);

    Map updata(Master master);

    Map delete(String id);
}

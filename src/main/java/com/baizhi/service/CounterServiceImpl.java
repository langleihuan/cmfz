package com.baizhi.service;

import com.baizhi.dao.CounterDao;
import com.baizhi.entity.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CounterServiceImpl implements CounterService {
    @Autowired
    private CounterDao counterDao;

    @Override
    public void addCounter(Counter counter) {
        counterDao.insertSelective(counter);
    }

    @Override
    public void delete(String id) {
        counterDao.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Counter counter) {
        counterDao.updateByPrimaryKeySelective(counter);
    }
}

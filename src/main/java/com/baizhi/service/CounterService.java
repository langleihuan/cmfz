package com.baizhi.service;

import com.baizhi.entity.Counter;

public interface CounterService {
    public void addCounter(Counter counter);

    public void delete(String id);

    public void update(Counter counter);
}

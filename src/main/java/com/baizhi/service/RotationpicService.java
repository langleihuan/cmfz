package com.baizhi.service;

import com.baizhi.entity.Rotationpic;

import java.util.Map;

public interface RotationpicService {
    public Map<String,Object> queryByPage(Integer size, Integer page);

    public void delete(String id);

    public void update(Rotationpic rotationpic);

    public void add(Rotationpic rotationpic);

    Map addrotationpic( Rotationpic rotationpic);
    Map updatarotationpic(Rotationpic rotationpic);
    Map deleterotationpic(String id);
}

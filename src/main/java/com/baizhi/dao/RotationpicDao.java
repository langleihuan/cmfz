package com.baizhi.dao;

import com.baizhi.entity.Rotationpic;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RotationpicDao extends Mapper<Rotationpic> {
    public List<Rotationpic> queryFive();
}

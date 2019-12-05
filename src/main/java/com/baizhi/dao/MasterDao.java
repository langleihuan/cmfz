package com.baizhi.dao;

import com.baizhi.entity.Master;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MasterDao extends Mapper<Master> {

    @Select("SELECT m.* from user u LEFT JOIN follow f on u.id = f.uid LEFT JOIN master m on m.id = f.mid where u.id=#{uid}")
    public List<Master> followMaster(String uid);
}

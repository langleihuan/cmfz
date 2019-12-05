package com.baizhi.dao;

import com.baizhi.entity.MapVO;
import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserDao extends Mapper<User> {

    public User login(String phone,String password);

    public Integer queryBy(@Param("sex") String sex,@Param("time") String time);

    public List<MapVO> queryByAera();

}

package com.baizhi.dao;

import com.baizhi.entity.Chapter;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ChapterDao extends Mapper<Chapter> {

    @Select("select * from chapter where album_id=#{album_id};")
    public List<Chapter> queryByAlbumId(String album_id);
}

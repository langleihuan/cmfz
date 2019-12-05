package com.baizhi.dao;

import com.baizhi.entity.Album;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AlbumDao extends Mapper<Album> {
    public List<Album> querySix();
}

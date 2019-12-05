package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.Map;

public interface AlbumService {
    public Map<String,Object> queryByPage(Integer size, Integer page);

    Map add(Album album);

    Map updata(Album album);

    Map delete(String id);
}

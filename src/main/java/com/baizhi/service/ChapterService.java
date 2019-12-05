package com.baizhi.service;

import com.baizhi.entity.Chapter;

import java.util.Map;

public interface ChapterService {
    public Map<String,Object> queryByPage(Integer size, Integer page,String album_id);

    Map add(Chapter chapter);

    Map updata(Chapter chapter);

    Map delete(String id);

}

package com.baizhi.service;

import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Chapter;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterDao chapterDao;

    @Override
    public Map<String, Object> queryByPage(Integer size, Integer page,String album_id) {
        Map<String,Object> map = new HashMap<>();
        Integer count = chapterDao.selectCount(new Chapter());
        Integer totalPage = 0;
        if(count%size==0){
            totalPage = count/size;
        }else{
            totalPage = count/size+1;
        }
        Integer start = (page-1)*size;
        map.put("records",count);
        map.put("page",page);
        map.put("total",totalPage);
        map.put("rows",chapterDao.selectByRowBounds(new Chapter().setAlbum_id(album_id),new RowBounds(start, size)));
        return map;
    }

    @Override
    public Map add(Chapter chapter) {
        HashMap hashMap = new HashMap();
        String s = UUID.randomUUID().toString();
        chapter.setId(s);
        chapterDao.insert(chapter);
        hashMap.put("chapterId",s);
        return hashMap;
    }

    @Override
    public Map updata(Chapter chapter) {
        HashMap hashMap = new HashMap();
        chapterDao.updateByPrimaryKeySelective(chapter);
        hashMap.put("chapterId",chapter.getId());
        return hashMap;
    }

    @Override
    public Map delete(String id) {
        HashMap hashMap = new HashMap();
        chapterDao.deleteByPrimaryKey(id);
        return hashMap;
    }
}

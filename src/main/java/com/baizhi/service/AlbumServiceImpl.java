package com.baizhi.service;

import com.baizhi.annotation.LogAnnotation;
import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.LogDao;
import com.baizhi.entity.Album;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDao albumDao;

    @Autowired
    private LogDao logDao;

    @Transactional(propagation = Propagation.SUPPORTS)
    @LogAnnotation(value = "查询专辑信息")
    @Override
    public Map<String, Object> queryByPage(Integer size, Integer page) {
        Map<String,Object> map = new HashMap<>();
        Integer count = albumDao.selectCount(new Album());
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
        map.put("rows",albumDao.selectByRowBounds(new Album(),new RowBounds(start, size)));
        return map;
    }

    @Override
    @LogAnnotation(value = "添加专辑信息")
    public Map add(Album album) {
        HashMap hashMap = new HashMap();
        String s = UUID.randomUUID().toString();
        album.setId(s);
        album.setCreate_time(new Date());
        albumDao.insert(album);
        hashMap.put("albumId",s);
        return hashMap;
    }

    @Override
    public Map updata(Album album) {
        HashMap hashMap = new HashMap();
        albumDao.updateByPrimaryKeySelective(album);
        hashMap.put("albumId",album.getId());
        return hashMap;
    }

    @Override
    public Map delete(String id) {
        HashMap hashMap = new HashMap();
        albumDao.deleteByPrimaryKey(id);
        return hashMap;
    }
}

package com.baizhi.service;

import com.baizhi.annotation.LogAnnotation;
import com.baizhi.dao.RotationpicDao;
import com.baizhi.entity.Rotationpic;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class RotationpicServiceImpl implements RotationpicService {
    @Autowired
    private RotationpicDao rotatonpicDao;

    @Override
    @LogAnnotation(value = "展示轮播图信息")
    public Map<String,Object> queryByPage(Integer size, Integer page){
        Map<String,Object> map = new HashMap<>();
        Integer count = rotatonpicDao.selectCount(new Rotationpic());
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
        map.put("rows",rotatonpicDao.selectByRowBounds(new Rotationpic(),new RowBounds(start, size)));
        return map;
    }

    @Override
    public Map addrotationpic(Rotationpic rotationpic) {
        HashMap hashMap = new HashMap();
        String s = UUID.randomUUID().toString();
        rotationpic.setId(s);
        rotationpic.setCreate_time(new Date());
        rotatonpicDao.insert(rotationpic);
        hashMap.put("rotationpicId",s);
        hashMap.put("status",200);
        return hashMap;
    }

    @Override
    public Map updatarotationpic(Rotationpic rotationpic) {
        HashMap hashMap = new HashMap();
        rotatonpicDao.updateByPrimaryKeySelective(rotationpic);
        hashMap.put("rotationpicId",rotationpic.getId());
        return hashMap;
    }

    @Override
    public Map deleterotationpic(String id) {
        HashMap hashMap = new HashMap();
        rotatonpicDao.deleteByPrimaryKey(id);
        return hashMap;
    }

    @Override
    public void delete(String id) {
        rotatonpicDao.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Rotationpic rotationpic) {
        rotatonpicDao.updateByPrimaryKeySelective(rotationpic);
    }

    @Override
    public void add(Rotationpic rotationpic) {
        rotatonpicDao.insertSelective(rotationpic.setId(UUID.randomUUID().toString()).setCreate_time(new Date()));
    }
}

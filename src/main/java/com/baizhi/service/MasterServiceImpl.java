package com.baizhi.service;

import com.baizhi.dao.MasterDao;
import com.baizhi.entity.Master;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class MasterServiceImpl implements MasterService {
    @Autowired
    private MasterDao masterDao;

    @Override
    public Map<String, Object> queryByPage(Integer size, Integer page) {
        Map<String,Object> map = new HashMap<>();
        Integer count = masterDao.selectCount(new Master());
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
        map.put("rows",masterDao.selectByRowBounds(new Master(),new RowBounds(start, size)));
        return map;
    }

    @Override
    public Map add(Master master) {
        HashMap hashMap = new HashMap();
        String s = UUID.randomUUID().toString();
        master.setId(s);
        masterDao.insert(master);
        hashMap.put("masterId",s);
        hashMap.put("status",200);
        return hashMap;
    }

    @Override
    public Map updata(Master master) {
        HashMap hashMap = new HashMap();
        masterDao.updateByPrimaryKeySelective(master);
        hashMap.put("masterId",master.getId());
        return hashMap;
    }

    @Override
    public Map delete(String id) {
        HashMap hashMap = new HashMap();
        masterDao.deleteByPrimaryKey(id);
        return hashMap;
    }
}

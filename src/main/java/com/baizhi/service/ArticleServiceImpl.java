package com.baizhi.service;

import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService{
    @Autowired
    private ArticleDao articleDao;


    @Override
    public void deleteArticle(String id) {
        articleDao.deleteByPrimaryKey(id);
    }

    @Override
    public void addArticle(Article article) {
        article.setId(UUID.randomUUID().toString());
        article.setPublish_date(new Date()) ;
        article.setCreate_time(new Date());
        article.setStatus("展示");
        articleDao.insert(article);
    }

    @Override
    public void updateArticle(Article article) {
        System.out.println(article+"+++++++++service++++++++++++");
        articleDao.updateByPrimaryKeySelective(article);
    }

    @Override
    public Map<String, Object> queryByPage(Integer size, Integer page) {
        Map<String,Object> map = new HashMap<>();
        Integer count = articleDao.queryTotalCounts();
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
        map.put("rows",articleDao.queryByPage(start,size));
        return map;
    }

    @Override
    public Map add(Article article) {
        HashMap hashMap = new HashMap();
        String s = UUID.randomUUID().toString();
        article.setId(s);
        article.setCreate_time(new Date());
        article.setPublish_date(new Date());
        articleDao.insert(article);
        hashMap.put("articleId",s);
        return hashMap;
    }

    @Override
    public Map updata(Article article) {
        HashMap hashMap = new HashMap();
        articleDao.updateByPrimaryKeySelective(article);
        hashMap.put("articleId",article.getId());
        return hashMap;
    }

    @Override
    public Map delete(String id) {
        HashMap hashMap = new HashMap();
        articleDao.deleteByPrimaryKey(id);
        return hashMap;
    }

}

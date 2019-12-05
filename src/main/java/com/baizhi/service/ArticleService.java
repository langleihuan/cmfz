package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.Map;

public interface ArticleService {
    public void deleteArticle(String id);

    public void addArticle(Article article);

    public void updateArticle(Article article);

    public Map<String,Object> queryByPage(Integer size, Integer page);

    Map add(Article article);

    Map updata(Article article);

    Map delete(String id);

}

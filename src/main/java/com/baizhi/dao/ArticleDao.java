package com.baizhi.dao;

import com.baizhi.entity.Article;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ArticleDao extends Mapper<Article> {
    public List<Article> queryByPage(@Param("start") Integer start, @Param("size")Integer size);

    public Integer queryTotalCounts();

    public void addArticle(Article article);

    public void updateArticle(Article article);

    public void deleteArticle(String id);

    public List<Article> queryThree();

    public Article queryById(String id);
}

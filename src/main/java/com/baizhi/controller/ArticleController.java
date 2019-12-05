package com.baizhi.controller;

import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import com.baizhi.util.HttpUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleDao articleDao;


    @RequestMapping("queryAll")
    public Map<String, Object> queryAll(Integer rows, Integer page){
        return articleService.queryByPage(rows,page);
    }

    @RequestMapping("showAllImgs")
    public Map showAllImgs(HttpSession session,HttpServletRequest request){
        // 1. 获取文件夹绝对路径
        String realPath = session.getServletContext().getRealPath("/upload/articleImg/");
        // 2. 准备返回的Json数据
        HashMap hashMap = new HashMap();
        ArrayList arrayList = new ArrayList();
        // 3. 获取目标文件夹
        File file = new File(realPath);
        File[] files = file.listFiles();
        // 4. 遍历文件夹中的文件
        for (File file1 : files) {
            // 5. 文件属性封装
            HashMap fileMap = new HashMap();
            fileMap.put("is_dir",false);
            fileMap.put("has_file",false);
            fileMap.put("filesize",file1.length());
            fileMap.put("is_photo",true);
            // 获取文件后缀 | 文件类型
            String extension = FilenameUtils.getExtension(file1.getName());
            fileMap.put("filetype",extension);
            fileMap.put("filename",file1.getName());
            // 获取文件上传时间 1. 截取时间戳 2. 创建格式转化对象 3. 格式类型转换
            String s = file1.getName().split("_")[0];
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String format = simpleDateFormat.format(new Date(Long.valueOf(s)));
            fileMap.put("datetime",format);
            arrayList.add(fileMap);
        }
        hashMap.put("file_list",arrayList);
        hashMap.put("total_count",arrayList.size());
        // 返回路径为 项目名 + 文件夹路径
        hashMap.put("current_url",request.getContextPath()+"/upload/articleImg/");
        return hashMap;
    }

    @RequestMapping("insertArticle")
    public void insertArticle( Article article,MultipartFile articleImg,HttpSession session,HttpServletRequest request){
        String dir = "/upload/articleImg/";
        String httpUrl = HttpUtil.getHttpUrl(articleImg, request, session, dir);
        article.setPicpath(httpUrl);
        articleService.addArticle(article);
    }

    @RequestMapping("deleteArticle")
    public void deleteArticle( String id){
        articleService.deleteArticle(id);
    }

    @RequestMapping("updateArticle")
    public void updateArticle( Article article,MultipartFile articleImg,HttpSession session,HttpServletRequest request){
        String dir = "/upload/articleImg/";
        String httpUrl = HttpUtil.getHttpUrl(articleImg, request, session, dir);
        article.setPicpath(httpUrl);
        articleService.updateArticle(article);
    }



    @RequestMapping("uploadImg")
    public Map uploadImg(MultipartFile imgFile,HttpSession session,HttpServletRequest request){
        HashMap hashMap = new HashMap();
        String dir = "/upload/articleImg/";
        try {
            String httpUrl = HttpUtil.getHttpUrl(imgFile, request, session, dir);
            hashMap.put("error",0);
            hashMap.put("url",httpUrl);
        } catch (Exception e) {
            hashMap.put("error",1);
            hashMap.put("message","上传错误");
            e.printStackTrace();
        }
        return hashMap;
    }

}

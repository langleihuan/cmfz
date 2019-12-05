package com.baizhi.controller;

import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import com.baizhi.util.MusicUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    @Autowired
    private ChapterDao chapterDao;

    @RequestMapping("queryAll")
    public Map<String,Object> queryAll(Integer rows, Integer page,String album_id){
        return chapterService.queryByPage(rows, page,album_id);
    }



    @RequestMapping("edit")
    @ResponseBody
    public Map edit(String oper, Chapter chapter, String id){
        Map hashMap = new HashMap();
        if(oper.equals("add")){
            hashMap = chapterService.add(chapter);
        }
        if(oper.equals("edit")){
            hashMap = chapterService.updata(chapter);
        }
        if(oper.equals("del")){
            hashMap = chapterService.delete(id);
        }
        return hashMap;
    }
    @RequestMapping("upload")
    @ResponseBody
    public void upload(MultipartFile audiopath, String chapterId, HttpSession session, HttpServletRequest request) throws UnknownHostException {
        System.out.println(audiopath.getOriginalFilename()+"+++++++++++++++++++++++++++++++++++++");
        // 获取路径
        String realPath = session.getServletContext().getRealPath("/upload/audio");
        // 判断路径文件夹是否存在
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        // 防止重名操作
        String originalFilename = audiopath.getOriginalFilename();
        originalFilename = new Date().getTime()+"_"+originalFilename;
        try {
            audiopath.transferTo(new File(realPath,originalFilename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 相对路径 : ../XX/XX/XX.jpg
        // 网络路径 : http://IP:端口/项目名/文件存放位置
        String http = request.getScheme();
        String localHost = InetAddress.getLocalHost().toString();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        String uri = http+"://"+localHost.split("/")[1]+":"+serverPort+contextPath+"/upload/audio/"+originalFilename;
        Chapter chapter = new Chapter();
        chapter.setId(chapterId);
        long size = audiopath.getSize();
        chapter.setSize(String.format("%.2f")+size/1024.0/1024.0+"MB");
        chapter.setAudiopath(uri);
        chapterDao.updateByPrimaryKeySelective(chapter);
        System.out.println(chapter);
        String path = chapter.getAudiopath().split("/")[6];
        String time = MusicUtil.getMp3TrackLength(new File(realPath,path));
        chapter.setTime(time);
        chapterDao.updateByPrimaryKeySelective(chapter);

    }


    @RequestMapping("/download")
    public void download(String audiopath, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String realPath = request.getSession().getServletContext().getRealPath("/upload/audio");
        FileInputStream is = new FileInputStream(new File(realPath, audiopath));
        ServletOutputStream os = response.getOutputStream();
        response.setHeader("content-disposition","attachment;fileName="+ URLEncoder.encode(audiopath,"UTF-8"));
        IOUtils.copy(is,os);
        IOUtils.closeQuietly(is);
        IOUtils.closeQuietly(os);
    }
}

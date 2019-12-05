package com.baizhi.controller;

import com.baizhi.dao.RotationpicDao;
import com.baizhi.entity.Rotationpic;
import com.baizhi.service.RotationpicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("rotationpic")
public class RotationpicController {
    @Autowired
    private RotationpicService rotationpicService;



    @Autowired
    private RotationpicDao rotationpicDao;

    @RequestMapping("queryAll")
    @ResponseBody
    public Map<String, Object> queryAll(Integer rows, Integer page){
        return rotationpicService.queryByPage(rows,page);
    }

    @RequestMapping("edit")
    @ResponseBody
    public Map edit(String oper,Rotationpic rotationpic,String id){
        Map hashMap = new HashMap();
        if(oper.equals("add")){
            hashMap = rotationpicService.addrotationpic(rotationpic);
        }
        if(oper.equals("edit")){
            hashMap = rotationpicService.updatarotationpic(rotationpic);
        }
        if(oper.equals("del")){
            hashMap = rotationpicService.deleterotationpic(id);
        }
        return hashMap;
    }
    @RequestMapping("upload")
    @ResponseBody
    public void upload(MultipartFile picpath, String rotationpicId, HttpSession session, HttpServletRequest request) throws UnknownHostException {
        // 获取路径
        String realPath = session.getServletContext().getRealPath("/upload/img");
        // 判断路径文件夹是否存在
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        // 防止重名操作
        String originalFilename = picpath.getOriginalFilename();
        originalFilename = new Date().getTime()+"_"+originalFilename;
        try {
            picpath.transferTo(new File(realPath,originalFilename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 相对路径 : ../XX/XX/XX.jpg
        // 网络路径 : http://IP:端口/项目名/文件存放位置
        String http = request.getScheme();
        String localHost = InetAddress.getLocalHost().toString();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        String uri = http+"://"+localHost.split("/")[1]+":"+serverPort+contextPath+"/upload/img/"+originalFilename;
        Rotationpic rotationpic = new Rotationpic();
        rotationpic.setId(rotationpicId);
        rotationpic.setPicpath(uri);
        rotationpicDao.updateByPrimaryKeySelective(rotationpic);

    }


    @ResponseBody
    @RequestMapping("option")
    public void option(String oper, Rotationpic rotationpic){
        if(oper.equals("add")){
            rotationpicService.add(rotationpic);
        }
        else if(oper.equals("del")){
            rotationpicService.delete(rotationpic.getId());
        }
        else if(oper.equals("edit")){
            System.out.println(rotationpic);
            rotationpicService.update(rotationpic);
        }
    }
}

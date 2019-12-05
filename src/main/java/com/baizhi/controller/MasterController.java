package com.baizhi.controller;

import com.baizhi.dao.MasterDao;
import com.baizhi.entity.Master;
import com.baizhi.service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("master")
public class MasterController {
    @Autowired
    private MasterService masterService;

    @Autowired
    private MasterDao masterDao;

    @RequestMapping("showMaster")
    public List<Master> showMaster(){
        List<Master> master = masterDao.selectAll();
        return master;
    }

    @RequestMapping("queryAll")
    public Map<String,Object> queryAll(Integer rows,Integer page){
        return masterService.queryByPage(rows,page);
    }

    @RequestMapping("edit")
    @ResponseBody
    public Map edit(String oper, Master master, String id){
        Map hashMap = new HashMap();
        if(oper.equals("add")){
            hashMap = masterService.add(master);
        }
        if(oper.equals("edit")){
            hashMap = masterService.updata(master);
        }
        if(oper.equals("del")){
            hashMap = masterService.delete(id);
        }
        return hashMap;
    }
    @RequestMapping("upload")
    @ResponseBody
    public void upload(MultipartFile head, String masterId, HttpSession session, HttpServletRequest request) throws UnknownHostException {
        // 获取路径
        String realPath = session.getServletContext().getRealPath("/upload/img");
        // 判断路径文件夹是否存在
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        // 防止重名操作
        String originalFilename = head.getOriginalFilename();
        originalFilename = new Date().getTime()+"_"+originalFilename;
        try {
            head.transferTo(new File(realPath,originalFilename));
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
        Master master = new Master();
        master.setId(masterId);
        master.setHead(uri);
        masterDao.updateByPrimaryKeySelective(master);

    }
}

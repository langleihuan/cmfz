package com.baizhi.controller;

import com.baizhi.dao.*;
import com.baizhi.entity.*;
import com.baizhi.service.CounterService;
import com.baizhi.service.CourseService;
import com.baizhi.service.RotationpicService;
import com.baizhi.service.UserService;
import com.baizhi.util.MessageUtil;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AlbumDao albumDao;
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private RotationpicDao rotationpicDao;
    @Autowired
    private RotationpicService rotationpicService;
    @Autowired
    private ChapterDao chapterDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private CounterDao counterDao;
    @Autowired
    private CourseService courseService;
    @Autowired
    private CounterService counterService;
    @Autowired
    private MasterDao masterDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private FollowDao followDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("queryAll")
    public Map<String,Object> queryAll(Integer rows,Integer page){
       return userService.queryByPage(rows, page);
    }


    @RequestMapping("edit")
    public Map edit(String oper, User user, String id){
        Map hashMap = new HashMap();
        if(oper.equals("add")){
            hashMap = userService.add(user);
        }
        if(oper.equals("edit")){
            hashMap = userService.updata(user);
        }
        if(oper.equals("del")){
            hashMap = userService.delete(id);
        }
        return hashMap;
    }

    //上传文件
    @RequestMapping("upload")
    public void upload(MultipartFile head, String userId, HttpSession session, HttpServletRequest request) throws UnknownHostException {
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
        User user = new User();
        user.setId(userId);
        user.setHead(uri);
        userDao.updateByPrimaryKeySelective(user);

        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-679409b5e9a0494286cdb54981475175");
        goEasy.publish("cmfz", "Hello World!");//content : json字符串
    }


    //登录接口
    @RequestMapping("login")
    public Map login(HttpServletRequest request,User user){
        Map map = new HashMap();
        User u = userService.queryOne(new User().setPhone(user.getPhone()));
        if(!u.getPhone().equals(user.getPhone())){
            map.put("-200","手机号错误!");
        }else {
            if(user.getPassword().equals(u.getPassword())){
                request.getSession().setAttribute("user", u);
                map.put("200","登录成功");
                map.put("user",u);
            }else{
                map.put("-200","密码错误！");
            }
        }
        return map;
    }

    //发送验证码
    @RequestMapping("sendMessage")
    public Map sendMessage(String phone){
        Map map = new HashMap();
        try {
            String code = "1234";
            MessageUtil.sendMessage(phone,code);
            // stringRedisTemplate 设置60秒时效
            ValueOperations<String, String> value = stringRedisTemplate.opsForValue();
            value.set(phone,code,120, TimeUnit.SECONDS);
        }catch (Exception e){
            map.put("-200","status");
            map.put("发送验证码失败","message");
        }


        map.put("200","status");
        map.put("发送验证码成功","message");
        return map;
    }

    //注册接口
    @RequestMapping("regist")
    public Map regist(String phone,String code){
        Map map = new HashMap();
        ValueOperations<String, String> value = stringRedisTemplate.opsForValue();
        String s = value.get(phone);
        if(s.equals(code)){
            map.put("200","stutus");
            map.put("验证成功","message");
        }
        return map;
    }

    @RequestMapping("supplementMessage")
    public Map supplementMessage(User user){
        Map map = new HashMap();
        try{
            userService.addUser(user);
            List<User> users = userDao.selectAll();
            map.put("User",users);
        }catch (Exception e){
            map.put("-200","status");
            map.put("补充信息失败","message");
        }
        map.put("200","status");
        map.put("补充信息成功","message");
        return map;
    }


    //@RequestMapping("login")
    //public String login(HttpServletRequest request,String captchaCode,User user){
    //    String securityCode = (String) request.getSession().getAttribute("securityCode");
    //    if(securityCode.equals(captchaCode)){
    //        try {
    //            User u = userService.login(user);
    //            if("冻结".equals(u.getStatus())){
    //                throw new RuntimeException("该用户已经被冻结");
    //            }else{
    //                if(u.getPhone()==null){
    //                    throw new RuntimeException("手机号错误！");
    //                }else {
    //                    String pwd = MD5Utils.getPassword(user.getPassword()+u.getSalt());
    //                    if(pwd.equals(u.getPassword())){
    //                        request.getSession().setAttribute("user", u);
    //                        return "success";
    //                    }else{
    //                        throw new RuntimeException("密码错误！");
    //                    }
    //                }
    //            }
    //        } catch (Exception e) {
    //            return e.getMessage();
    //        }
    //    }else return "验证码错误!";
    //}


    //根据性别与时间查询
    @RequestMapping("queryBy")
    public Map queryBy(String sex,String time){
        return userService.queryBy(sex, time);
    }

    //根据地区查询
    @RequestMapping("queryByArea")
    public List<MapVO> queryByArea(){
        return userService.queryByArea();
    }

    //首页接口
    @RequestMapping("queryFirstPage")
    public Map queryFirstPage(String uid,String type,String subType){
        Map map = new HashMap();
        if(uid!=null){
            if(type.equals("all")){
                List<Rotationpic> rotationpics = rotationpicDao.queryFive();
                List<Album> albums = albumDao.querySix();
                List<Article> articles = articleDao.queryThree();
                map.put("Rotationpic",rotationpics);
                map.put("Album",albums);
                map.put("Article",articles);
            }
           else if(type.equals("wen")){
                List<Album> albums = albumDao.selectAll();
                map.put("Album",albums);
            }
           else if(type.equals("si")){
                //进入上师言教界面
               if(subType.equals("ssjy")){
                   List<Object> list = new ArrayList<>();
                   List<Master> masters = masterDao.followMaster(uid);
                   for (Master master : masters) {
                       List<Article> select = articleDao.select(new Article().setMaster_id(master.getId()));
                       list.add(select);
                   }
                   map.put("Article",list);
               }
                //进入显密法要界面
               if(subType.equals("xmfy")){
                   List<Article> articles = articleDao.selectAll();
                   map.put("Article",articles);
               }

            }
        }

        return map;
    }

    //文章详情接口
    @RequestMapping("articleDetails")
    public Map articleDetails(String uid,String id){
        Map map = new HashMap();
        if(uid!=null){
            Article article = articleDao.queryById(id);
            map.put("Article",article);
        }
        return map;
    }


    //专辑详情接口
    @RequestMapping("albumDetails")
    public Map albumDetails(String uid,String id){
        Map map = new HashMap();
        if(uid!=null){
            Album album = albumDao.selectByPrimaryKey(id);
            List<Chapter> chapters = chapterDao.queryByAlbumId(id);
            map.put("Album",album);
            map.put("Chapter",chapters);
        }
        return map;
    }


    //展示功课接口
    @RequestMapping("showCourse")
    public Map showCourse(String uid){
        Map map = new HashMap();
        if(uid!=null){
            List<Course> courses = courseDao.select(new Course().setUser_id(uid));
            map.put("Course",courses);
        }

        return map;
    }

    //添加功课接口
    @RequestMapping("addCourse")
    public Map addCourse(String uid){
        Map map = new HashMap();
        if(uid!=null) {
            courseService.addCourse(new Course().setId(UUID.randomUUID().toString()).setName("你猜").setCategory("私有").setCreate_time(new Date()).setUser_id(uid));
            List<Course> courses = courseDao.selectAll();
            map.put("Course", courses);
        }
        return map;
    }

    //删除功课
    @RequestMapping("deleteCourse")
    public Map deleteCourse(String uid,String id){
        Map map = new HashMap();
        if(uid!=null){
            courseService.delete(id);
            List<Course> courses = courseDao.selectAll();
            map.put("Course", courses);
        }
        return map;
    }

    //展示计数器
    @RequestMapping("showCounter")
    public Map showCounter(String uid,String id){
        Map map = new HashMap();
        if(uid!=null){
            List<Counter> counters = counterDao.select(new Counter().setUser_id(uid).setCourse_id(id));
            map.put("Counter",counters);
        }
        return map;
    }

    //添加计数器
    @RequestMapping("addCounter")
    public Map addCounter(String uid,String id){
        Map map = new HashMap();
        if(uid!=null) {
            counterService.addCounter(new Counter().setId(UUID.randomUUID().toString()).setName("你猜").setCount(111).setCreate_time(new Date()).setUser_id(uid).setCourse_id(id));
            List<Counter> counters = counterDao.selectAll();
            map.put("Counter", counters);
        }
        return map;
    }

    //删除计数器
    @RequestMapping("deleteCounter")
    public Map deleteCounter(String uid,String id){
        Map map = new HashMap();
        if(uid!=null){
            counterService.delete(id);
            List<Course> courses = courseDao.selectAll();
            map.put("Course", courses);
        }
        return map;
    }

    //更新计数器
    @RequestMapping("updateCounter")
    public Map update(String uid,String id,Integer count){
        Map map = new HashMap();
        if(uid!=null){
            Counter counter = counterDao.selectByPrimaryKey(id);
            counterService.update(counter.setCount(count));
            List<Counter> counters = counterDao.selectAll();
            map.put("Counter",counters);
        }
        return map;
    }

    //修改个人信息
    @RequestMapping("updatePerson")
    public Map updatePerson(String uid,String sex,String head,String address,String signature,String username,String password){
        Map map = new HashMap();
        if(uid!=null){
            User user = userDao.selectByPrimaryKey(uid);
            userService.updata(user.setSex(sex).setHead(head).setAddress(address).setSignature(signature).setUsername(username).setPassword(password));
            List<User> users = userDao.selectAll();
            map.put("user",users);
        }
        return map;
    }

    //展示上师接口
    @RequestMapping("showMaster")
    public Map showMaster(String uid){
        Map map = new HashMap();
        if(uid!=null) {
            List<Master> masters = masterDao.selectAll();
            map.put("Master",masters);
        }
        return map;
    }

    //展示关注上师
    @RequestMapping("follow")
    public Map follow(String uid){
        Map map = new HashMap();
        if(uid!=null) {
            List<Master> masters = masterDao.followMaster(uid);
            map.put("Master",masters);
        }
        return map;
    }

    //添加关注上师
    @RequestMapping("addFollow")
    public Map follow(String uid,String id){
        Map map = new HashMap();
        if(uid!=null) {
            followDao.insert(new Follow().setUid(uid).setMid(id));
            List<Follow> follows = followDao.selectAll();
            map.put("Follow",follows);
        }
        return map;
    }
}

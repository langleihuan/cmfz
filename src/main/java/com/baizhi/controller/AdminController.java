package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("login")
    @ResponseBody
    public String login(Admin admin, HttpServletRequest request, String captchaCode){
        String securityCode = (String) request.getSession().getAttribute("securityCode");
        if (securityCode.equals(captchaCode)){
            try{
                Admin login = adminService.login(admin.getUsername(),admin.getPassword());
                if(login==null) {
                    throw new RuntimeException("用户名或者密码错误");
                }else {
                    request.getSession().setAttribute("admin",login);
                    return "success";
                }
            }catch (Exception e){
                return e.getMessage();
            }
        }else
        return "验证码错误！";
    }
}

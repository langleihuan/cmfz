package com.baizhi.aspect;

import com.baizhi.annotation.LogAnnotation;
import com.baizhi.dao.LogDao;
import com.baizhi.entity.Admin;
import com.baizhi.entity.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.UUID;

@Aspect
@Configuration
public class LogAspect {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private LogDao logDao;

    @Around("@annotation(com.baizhi.annotation.LogAnnotation)")
    public Object addLog(ProceedingJoinPoint proceedingJoinPoint){
        /*
            谁 时间 事件 成功与否
         */
        HttpSession session = request.getSession();
        //session.setAttribute("admin","admin");
        Admin admin = (Admin) session.getAttribute("admin");
        // 时间
        Date date = new Date();
        // 获取方法名
        String name = proceedingJoinPoint.getSignature().getName();
        // 获取注解信息
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        LogAnnotation annotation = signature.getMethod().getAnnotation(LogAnnotation.class);
        String value = annotation.value();
        System.out.println(value);
        String status=null;
        try {
            Object proceed = proceedingJoinPoint.proceed();
            status = "success";
            System.out.println(admin+" "+date+" "+name+" "+status);
            return proceed;
        } catch (Throwable throwable) {
            status = "error";
            System.out.println(admin+" "+date+" "+name+" "+status);
            throwable.printStackTrace();
            return null;
        }finally {
            logDao.insert(new Log().setId(UUID.randomUUID().toString()).setAdmin(admin.getUsername()).setAction(name).setStatus(status).setTime(date));
        }

    }
}

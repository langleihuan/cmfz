//package com.baizhi.task;//package com.baizhi.task;
//
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
///**
// * Created by HIAPAD on 2019/12/3.
// * fixedDelay: 当定时任务执行完毕时开始计时
// * fixedRate: 当定时任务开启时计时
// */
//@Component
//@Async
//public class TestSpringTask {
//
//    @Scheduled(fixedDelay = 3000)
//    public void task01() throws InterruptedException {
//        System.out.println(Thread.currentThread().getName());
//        Thread.sleep(5000);
//        System.out.println(Thread.currentThread().getName());
//        System.out.println("task1"+new Date());
//    }
//
//    @Scheduled(fixedRate = 3000)
//    public void task02() throws InterruptedException {
//        Thread.sleep(5000);
//        //System.out.println(Thread.currentThread().getName());
//        System.out.println("task2"+new Date());
//    }
//}

package com.baizhi.task;

import com.alibaba.excel.EasyExcel;
import com.baizhi.dao.MasterDao;
import com.baizhi.entity.Master;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by HIAPAD on 2019/12/3.
 */
@Component
public class ControllerTask {
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Autowired
    private MasterDao masterDao;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
        return new ThreadPoolTaskScheduler();
    }
    public void run(){

        Runnable runnable = new Runnable() {
            @Override
            public void run(){
                // 业务代码
                String fileName = "D:\\JAVA_POI\\数据\\"+new Date().getTime()+"MasterData.xlsx";
                EasyExcel.write(fileName, Master.class).sheet("模板").doWrite(data());
                System.out.println("数据导出成功！");
            }


            private List<Master> data(){
                List<Master> masters = masterDao.selectAll();
                return masters;
            }
        };
        threadPoolTaskScheduler.schedule(runnable,new CronTrigger("0 0 12 ? * SUN"));
    }
    public void shutdown(){
        threadPoolTaskScheduler.shutdown();
    }
}

package com.baizhi.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by HIAPAD on 2019/12/3.
 */
@RestController
@RequestMapping("test")
public class TestController {
    @Autowired
    private ControllerTask controllerTask;
    @RequestMapping("test")
    public String test(){
        controllerTask.run();
        return "OK";
    }
}

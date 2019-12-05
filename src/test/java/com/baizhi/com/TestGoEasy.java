package com.baizhi.com;

import io.goeasy.GoEasy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestGoEasy {
    @Test
    public void goEasy(){
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-eb29f9235303415c90afd5cb262f9f85");
        goEasy.publish("cmfz", "Hello World!");//content : json字符串
    }
}

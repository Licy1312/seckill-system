package com.jinmu.service.impl;

import com.jinmu.dao.model.Seckill;
import com.jinmu.service.SeckillService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Lingling on 2017/3/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:configure/spring.xml")
public class TestSeckillServiceImpl {
    @Autowired
    private SeckillService seckillService;
    @Test
    public void testGetById(){
        long id = 6;
        Seckill temp = seckillService.getById(id);
        System.out.println(temp);
    }

}

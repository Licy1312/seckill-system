package com.jinmu.service.utils;


import com.jinmu.dao.model.Seckill;
import com.jinmu.provider.dto.SeckillDTO;

/**
 * Created by Lingling on 2017/3/5.
 */
public class DoToDTO {
    public static SeckillDTO seckillDtoHelp(Seckill seckill){
        SeckillDTO temp = new SeckillDTO();
        temp.setSeckillId(seckill.getSeckillId());
        temp.setName(seckill.getName());
        temp.setNumber(seckill.getNumber());
        temp.setStartTime(seckill.getStartTime());
        temp.setEndTime(seckill.getEndTime());
        return temp;
    }
}

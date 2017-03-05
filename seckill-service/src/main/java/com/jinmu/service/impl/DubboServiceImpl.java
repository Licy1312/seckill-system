package com.jinmu.service.impl;


import com.jinmu.dao.model.Seckill;
import com.jinmu.provider.DubboService;
import com.jinmu.provider.dto.SeckillDTO;
import com.jinmu.service.SeckillService;
import com.jinmu.service.utils.DoToDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by Lingling on 2017/3/5.
 */
@Service
public class DubboServiceImpl implements DubboService {
    @Autowired
    private SeckillService seckillService;
    @Override
    public SeckillDTO getSeckill(Long seckillId) {
        Seckill seckill = seckillService.getById(seckillId);
        if(seckill != null){
            return DoToDTO.seckillDtoHelp(seckill);
        }
        return null;
    }
}

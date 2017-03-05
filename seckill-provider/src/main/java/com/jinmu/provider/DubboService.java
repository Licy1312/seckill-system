package com.jinmu.provider;

import com.jinmu.provider.dto.SeckillDTO;
import org.springframework.stereotype.Component;

/**
 * Created by Lingling on 2017/3/5.
 */
public interface DubboService {
    /**
     *通过秒杀Id获取商品的信息
     * @param seckillId
     * @return
     */
    public SeckillDTO getSeckill(Long seckillId);
}

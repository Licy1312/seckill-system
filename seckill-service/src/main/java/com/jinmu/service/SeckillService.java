package com.jinmu.service;


import com.jinmu.service.dto.Exposer;
import com.jinmu.service.dto.SeckillExecution;
import com.jinmu.service.exception.RepeatKillException;
import com.jinmu.service.exception.SeckillCloseException;
import com.jinmu.service.exception.SeckillException;
import com.jinmu.dao.model.Seckill;
import org.springframework.stereotype.Component;

import java.util.List;

/**方法定义粒度，参数，返回类型，异常处理
 * Created by Lingling on 2016/12/8.
 */
@Component
public interface SeckillService {
    /**
     * 查询所有秒杀商品
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 查询某一个秒杀商品
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 秒杀开启输出秒杀接口地址
     * 否则输出服务器系统当前的时间和秒杀时间
     * @param seckillId
     * @return
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException,SeckillCloseException,RepeatKillException;


}

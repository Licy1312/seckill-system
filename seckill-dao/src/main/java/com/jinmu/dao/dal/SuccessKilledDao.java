package com.jinmu.dao.dal;

import com.jinmu.dao.model.SuccessKilled;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;


/**
 * Created by Lingling on 2016/12/7.
 */
@Component
public interface SuccessKilledDao {
    /**
     * 插入购买成功的记录（利用联合主键避免重复秒杀）
     * @param seckillId
     * @param userPhone
     * @return
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    /**
     *根据id查询SuccessKilled并携带秒杀产品对象实体
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

}

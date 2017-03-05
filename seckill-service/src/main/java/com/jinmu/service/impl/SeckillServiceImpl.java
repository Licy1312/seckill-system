package com.jinmu.service.impl;


import com.jinmu.dao.dal.SeckillDao;
import com.jinmu.dao.dal.SuccessKilledDao;
import com.jinmu.service.dto.Exposer;
import com.jinmu.service.dto.SeckillExecution;
import com.jinmu.service.enums.SeckillStateEnum;
import com.jinmu.service.exception.RepeatKillException;
import com.jinmu.service.exception.SeckillCloseException;
import com.jinmu.service.exception.SeckillException;
import com.jinmu.dao.model.Seckill;
import com.jinmu.dao.model.SuccessKilled;
import com.jinmu.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;


/**
 * Created by Lingling on 2016/12/8.
 */
@Service
public class SeckillServiceImpl implements SeckillService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccessKilledDao successKilledDao;
    private final String slat="afa33h95886**^)$HDHF_1355mbmjghkqrbt";
    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0,10);
    }
    @Override
    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }
    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = seckillDao.queryById(seckillId);

        if(seckill == null){
            //返回一个秒杀未开启的对象
            return new Exposer(false,seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();
        if(nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()){
            return new Exposer(false,seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
        }
        //根据商品id进行md5加密
        String md5 = getMD5(seckillId);
        return new Exposer(true,md5,seckillId);
    }
    /**
     *使用注解控制事务方法的优点：
     * 1.保证事务的执行时间尽可能的短，不要穿插其他网络操作RPC、http等，如果有可以进行上层剥离
     * 2.可以做到不是所有的方法需要事务，如只有一条修改操作，或者是只读操作不需要事务
     */
    @Override
    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, SeckillCloseException, RepeatKillException {
        if (md5 == null|| !md5.equals(getMD5(seckillId))){
            throw new SeckillException("dal data rewrite");
        }
        //执行秒杀逻辑：减库存+记录秒杀成功信息
        Date nowTime = new Date();
        try{
            //减库存
            int updateCount = seckillDao.reduceNumber(seckillId,nowTime);
            if(updateCount <= 0){
                //更新失败，减库存失败，秒杀结束
                throw new SeckillCloseException("dal is closed");
            }else{
                //记录秒杀成功信息
                int insertCount = successKilledDao.insertSuccessKilled(seckillId,userPhone);
                if(insertCount <= 0){
                    //重复秒杀异常
                    throw new RepeatKillException("dal repeated");
                }else{
                    //秒杀成功
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
                    return  new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS,successKilled);
                }
            }
        }catch (SeckillCloseException e1){
            throw e1;
        }catch (RepeatKillException e2){
            throw e2;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            //所有编译期异常，转化为运行期异常
            throw  new SeckillException("dal inner error:"+e.getMessage());
        }
    }
    private String getMD5(long seckillId){
        String base = seckillId + "/" +slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
}

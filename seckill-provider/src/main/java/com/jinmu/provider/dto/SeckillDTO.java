package com.jinmu.provider.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Lingling on 2017/3/5.
 */
@Getter
@Setter
@ToString
public class SeckillDTO implements Serializable {
    /**
     * 秒杀ID
     */
    private long seckillId;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品剩余数量
     */
    private int number;
    /**
     * 秒杀开启时间
     */
    private Date startTime;
    /**
     * 秒杀结束时间
     */
    private Date endTime;
}

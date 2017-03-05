package com.jinmu.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**用于暴露秒杀地址的dto
 * Created by Lingling on 2016/12/8.
 */
@Getter
@Setter
@ToString
public class Exposer {
    //是否开启秒杀
    private boolean exposed;
    //对秒杀地址加密
    private  String md5;
    //秒杀商品id
    private long seckillId;
    //系统当前时间(毫秒)
    private long now;
    private long start;
    private long end;
    public Exposer(boolean exposed, String md5, long seckillId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    public Exposer(boolean exposed, long seckillId,long now, long start, long end) {
        this.exposed = exposed;
        this.seckillId = seckillId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public Exposer( boolean exposed,long seckillId) {
        this.exposed = exposed;
        this.seckillId = seckillId;
    }
}

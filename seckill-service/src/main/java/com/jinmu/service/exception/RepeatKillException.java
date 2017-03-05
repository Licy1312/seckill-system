package com.jinmu.service.exception;

/**重复秒杀异常（运行期异常）
 * spring申明式事务只接受运行期异常回滚策略
 * Created by Lingling on 2016/12/8.
 */
public class RepeatKillException extends SeckillException{
    public RepeatKillException(String message){
        super(message);
    }

    public RepeatKillException(String message,Throwable cause){
        super(message,cause);
    }
}

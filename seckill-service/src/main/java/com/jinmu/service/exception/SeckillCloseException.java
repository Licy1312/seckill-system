package com.jinmu.service.exception;

/**秒杀关闭异常
 * Created by Lingling on 2016/12/8.
 */
public class SeckillCloseException extends SeckillException {
    public SeckillCloseException(String message){
        super(message);
    }

    public SeckillCloseException(String message,Throwable cause){
        super(message,cause);
    }
}

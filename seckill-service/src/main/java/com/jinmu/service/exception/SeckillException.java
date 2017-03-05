package com.jinmu.service.exception;

/**
 * Created by Lingling on 2016/12/8.
 */
public class SeckillException extends RuntimeException{
    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}

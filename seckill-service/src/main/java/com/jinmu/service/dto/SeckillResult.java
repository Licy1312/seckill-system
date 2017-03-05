package com.jinmu.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**用于封装所有ajax请求返回的json结果
 * Created by Lingling on 2016/12/10.
 */
@Getter
@Setter
@ToString
public class SeckillResult<T> {
    private boolean success;
    private  T data;
    private  String error;

    public SeckillResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public SeckillResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

}

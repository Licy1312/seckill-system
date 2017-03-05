/**
 * Created by Lingling on 2016/12/10.
 */
//存放主要交互逻辑js代码
//javascript模块化
var seckill = {
    //封装秒杀相关的ajax的url
    URL: {
        //获取当前系统时间的url
        now: function(){
            return '/dal/main/time/now';
        },
        //获取秒杀地址的url
        exposer: function(seckillId){
            return '/dal/main/'+seckillId+'/exposer';
        },
        //执行秒杀的url
        execution: function(seckillId,md5){
            return '/dal/main/'+seckillId+'/'+md5+'/execution';
        }
    },
    //获取秒杀地址，控制显示逻辑，执行秒杀
    handleSeckillkill: function(seckillId,node){
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
        $.post(seckill.URL.exposer(seckillId),{},function(result){
            //在回调函数中执行交互流程
            if(result && result['success']){
                var exposer = result['data'];
                if(exposer['exposed']){
                    //开启秒杀,获取到了秒杀地址
                    var md5 = exposer['md5'];
                    var killUrl = seckill.URL.execution(seckillId,md5);
                    console.log("killUrl:"+killUrl);
                    //绑定一次点击事件,执行秒杀
                    $('#killBtn').one('click',function(){
                        //1.先禁用按钮
                        $(this).addClass('disabled');
                        //2.发送秒杀请求
                        $.post(killUrl,{},function(result){
                            if(result && result['success']){
                                var killResult = result['data'];
                                var state =killResult['state'];
                                var stateInfo = killResult['stateInfo'];
                                //3.显示秒杀结果
                                node.html('<span class="label label-success">'+stateInfo+'</span>');
                            }
                        });
                    });
                    node.show();
                }else{
                    //此处又出现秒杀未开启的原因，可能时间差等
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    //重新计时
                    seckill.countdown(seckillId,now,start,end);
                }
            }else{
                console.log('result:'+result);
            }
        });
    },
    //验证手机号
    validatePhone: function(phone){
      if(phone && phone.length == 11 && !isNaN(phone)){
          return true;
      } else{
          return false;
      }
    },
    //进行倒计时
    countdown: function(seckillId,nowTime,startTime,endTime){
        var seckillBox = $('#dal-box');
        if(nowTime > endTime){
            seckillBox.html('秒杀结束！');
        }else if(nowTime < startTime){
            //秒杀未开启
            var killTime = new Date(startTime + 1000);
            seckillBox.countdown(killTime,function(event){
               //事件格式
                var format = event.strftime('秒杀倒计时: %D天 %H时 %M分 %S秒');
                seckillBox.html(format);
                //计时完成，获取秒杀地址，控制显示逻辑，执行秒杀
            }).on('finish.countdown',function(){
                seckill.handleSeckillkill(seckillId,seckillBox);
            });
        }else{
            //秒杀开始
            seckill.handleSeckillkill(seckillId,seckillBox);
        }
    },
    //详情页秒杀逻辑
    detail: {
        init: function(params){
            //手机验证和登录，计时交互
            //规划我们的交互流程
            //在cookie中查找手机号
            var killPhone = $.cookie('killPhone');
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
            console.log("startTime="+startTime,"endTime="+endTime);
            //验证手机号
            if(!seckill.validatePhone(killPhone)){
               //绑定phone，控制输出
                var killPhoneModal = $('#killPhoneModal');
                //显示弹出层
                killPhoneModal.modal({
                    show:true,//显示弹出框
                    backdrop:'static',//禁止位置关闭
                    keyboard:false //关闭键盘事件
                });
                $('#killPhoneBtn').click(function(){
                    var inputPhone = $('#killPhoneKey').val();
                    if(seckill.validatePhone(inputPhone)){
                        //电话写入cookie,时间7天，存在的路径
                        $.cookie('killPhone',inputPhone,{expires:7,path:'/dal/main'});
                        //刷新页面
                        window.location.reload();
                    }else{
                        $("#killPhoneMessage").hide().html('<label class="label label-danger">手机号码格式不对</label>').show(500);
                    }
                });
            }
            $.get(seckill.URL.now(),{},function(result){
                if(result && result['data']){
                    var nowTime = result['data'];
                    seckill.countdown(seckillId,nowTime,startTime,endTime);
                }else{
                    console.log('result:'+result);
                }
            });
        }
    }
}
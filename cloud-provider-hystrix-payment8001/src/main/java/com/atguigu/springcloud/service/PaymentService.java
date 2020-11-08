package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;



@Service
public class PaymentService {

    public String paymentInfo_OK(Integer id) {
        return ("Thread pool: " + Thread.currentThread().getName() + "paymentInfo_OK"  + "\t" + "O(n_n)");
        //" + Thread.currentThread().getName() + "paymentInfo_OK" + id.toString() + "\t" + "O(n_n)"
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    public String paymentInfo_Timeout(Integer id)  {
        try{
            TimeUnit.SECONDS.sleep(4);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        //int temp = 10/ 0;
        return "Thread pool: " + Thread.currentThread().getName() + "paymentInfo_Timeout" + id.toString() + "\t" + ":(" + "take 4 seconds";
    }

    public String paymentInfo_TimeOutHandler(Integer id) {
        return "Thread pool: " + Thread.currentThread().getName() + "paymentInfo_TimeoutHandler, id" + id + "\t" + "o(T--T)o";
    }

    // service break
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        if(id < 0) {
            throw new RuntimeException("**** id can not be negative");
        }
        String serialNumber = IdUtil.simpleUUID();

        return Thread.currentThread().getName() + "\t" + "call successful, serialNumber: " + serialNumber;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id) {
        return "id can not be negative, please try later id:" + id;
    }
}

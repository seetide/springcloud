package com.atguiguspringcloud.controller;

import com.atguiguspringcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService service;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id")  Integer id) {
        return service.paymentInfo_OK(id);
    }

    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
//    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties = {
//            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "1500")
//    })
    @HystrixCommand
    public String paymentInfo_Timeout(Integer id)  {

        int temp = 10/ 0;
        return "Thread pool: " + Thread.currentThread().getName() + "paymentInfo_Timeout" + id.toString() + "\t" + ":(" + "take 4 seconds";
    }

    public String paymentInfo_TimeOutHandler(Integer id) {
        return "I am consumer. the payment service is busy, please try later: "  + "paymentInfo_TimeoutHandler, id" + id + "\t" + "o(T--T)o";
    }


    // global fallback
    public String payment_Global_FallbackMethod() {
        return "Global exception handler. Please try again later.";
    }
}

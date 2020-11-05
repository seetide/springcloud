package com.atguigu.springcloud.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;



@Service
public class PaymentService {

    public String paymentInfo_OK(Integer id) {
        return ("Thread pool: " + Thread.currentThread().getName() + "paymentInfo_OK"  + "\t" + "O(n_n)");
        //" + Thread.currentThread().getName() + "paymentInfo_OK" + id.toString() + "\t" + "O(n_n)"
    }

    public String paymentInfo_Timeout(Integer id) throws InterruptedException {
        try{
            TimeUnit.SECONDS.sleep(4);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Thread pool: " + Thread.currentThread().getName() + "paymentInfo_Timeout" + id.toString() + "\t" + ":(" + "take 3 seconds";
    }
}

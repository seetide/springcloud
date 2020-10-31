package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);

        log.info("The insertion result: " + result);

        if(result > 0) return new CommonResult(200, "The insertion is successful", result);
        else return new CommonResult(444, "The insersion failed", null);
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentId(@PathVariable("id") Long id) {
        Payment result = paymentService.getPaymentById(id);

        log.info("The insertion result: " + result);

        if(result != null) return new CommonResult(200, "The insertion is successful", result);
        else return new CommonResult(444, "The query failed. Query id: " + id, null);
    }
}



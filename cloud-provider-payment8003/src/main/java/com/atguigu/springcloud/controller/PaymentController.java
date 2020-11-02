package com.atguigu.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
@RestController
@Slf4j
public class PaymentController {

    @Value("${server.port")
    private String serverPort;

    @GetMapping(value = "/payment/zk")
    public String paymentzk() {
        return "Springcloud with zookeeper: " + serverPort;
    }
}

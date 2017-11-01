package com.orrin.sca.common.service.uaa.server.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class IndexController {

    @Value("${sys_env}")
    private String sysEvn;

    @GetMapping(path = "/sysenv")
    public String sysEvn() {
        return this.sysEvn;
    }

    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public String test() {
        return "test";
    }
}

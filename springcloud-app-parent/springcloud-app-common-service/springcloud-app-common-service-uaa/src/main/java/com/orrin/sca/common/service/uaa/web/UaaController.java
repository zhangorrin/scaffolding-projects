package com.orrin.sca.common.service.uaa.web;

import com.orrin.sca.common.service.uaa.service.SysUsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
public class UaaController {
    private static final Logger logger = LoggerFactory.getLogger(UaaController.class);

    @Autowired
    private SysUsersService sysUsersService;

    @RequestMapping(path = "index", method = RequestMethod.GET)
    public Object index(HttpServletRequest request){

        String accessA = OAuth2AuthenticationDetails.ACCESS_TOKEN_VALUE;

        Object accessTokenValue = request.getAttribute(accessA);

        return sysUsersService.findSysUsersNoCriteria(0,20);
    }

}

package com.orrin.scab.product.oauth.resource.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
public class IndexController {
    @RequestMapping(path = "api/index", method = RequestMethod.GET)
    String getMessages(Principal principal, HttpServletRequest request) {

        String url = request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString();

        return principal.getName();
    }
}

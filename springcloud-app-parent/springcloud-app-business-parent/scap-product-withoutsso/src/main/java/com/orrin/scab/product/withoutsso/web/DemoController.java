package com.orrin.scab.product.withoutsso.web;

import com.orrin.scab.product.withoutsso.model.ProductDetail;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@RestController
@RequestMapping("/product")
public class DemoController {

    @RequestMapping(method = RequestMethod.GET)
    public ProductDetail getProductDetail(HttpServletRequest request){
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String thisname = headerNames.nextElement();
            String thisvalue = request.getHeader(thisname);
            System.out.println("header name = "+thisname + "	, value = "+ thisvalue);
        }

        Enumeration<String> parameterNames = request.getParameterNames();
        while(parameterNames.hasMoreElements()){
            String thisname = parameterNames.nextElement();
            String thisvalue = request.getParameter(thisname);
            System.out.println("parameterNames name = "+thisname + "	, value = "+ thisvalue);
        }

        Enumeration<String> attributeNames = request.getAttributeNames();
        while(attributeNames.hasMoreElements()){
            String thisname = attributeNames.nextElement();
            Object thisvalue = request.getAttribute(thisname);
            System.out.println("Attribute name = "+thisname + "	, value = "+ thisvalue.toString());
        }

        ProductDetail productDetail = new ProductDetail();
        productDetail.setProductId("1");
        productDetail.setProductName("one");
        productDetail.setProductDescId("desc1");

        return productDetail;
    }
}

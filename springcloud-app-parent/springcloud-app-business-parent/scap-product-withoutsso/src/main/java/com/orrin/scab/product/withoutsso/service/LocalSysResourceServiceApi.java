package com.orrin.scab.product.withoutsso.service;


import com.orrin.sca.common.service.uaa.service.SysResourceServiceApi;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "sys-resource-compose")
public interface LocalSysResourceServiceApi extends SysResourceServiceApi {
}

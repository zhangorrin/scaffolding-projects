package com.orrin.sca.common.service.uaa.client.feignclient;

import com.orrin.sca.common.service.uaa.domain.SysResources;
import com.orrin.sca.component.privilege.annotation.ResourcePrivilegeEntity;
import com.orrin.sca.framework.core.model.ResponseResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;

@FeignClient(value = "common-service-uaa")
public interface SysResourceServiceApi {
    @RequestMapping(path = "/api/resource/addprivilege", method = RequestMethod.POST)
    ResponseResult<SysResources> insertPrivilege(ResourcePrivilegeEntity resourcePrivilegeEntity);

    @RequestMapping(path = "/api/resource/authResources", method = RequestMethod.GET)
    ArrayList<HashMap<String,String>>  findAuthResources();
}

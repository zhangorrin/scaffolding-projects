package com.orrin.sca.common.service.uaa.client.feignclient;

import com.orrin.sca.common.service.uaa.client.domain.SysResourcesEntity;
import com.orrin.sca.common.service.uaa.client.vo.ResourceBriefInfo;
import com.orrin.sca.component.privilege.annotation.ResourcePrivilegeEntity;
import com.orrin.sca.framework.core.config.FeignClientsConfiguration;
import com.orrin.sca.framework.core.model.ResponseResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "common-service-uaa", configuration = FeignClientsConfiguration.class)
public interface SysResourceServiceApi {
    @RequestMapping(path = "/api/resource/feign/addprivilege", method = RequestMethod.POST)
    ResponseResult<SysResourcesEntity> insertPrivilege(@RequestBody ResourcePrivilegeEntity resourcePrivilegeEntity);

    @RequestMapping(path = "/api/resource/feign/authresources", method = RequestMethod.GET)
    List<ResourceBriefInfo> findAuthResources();
}

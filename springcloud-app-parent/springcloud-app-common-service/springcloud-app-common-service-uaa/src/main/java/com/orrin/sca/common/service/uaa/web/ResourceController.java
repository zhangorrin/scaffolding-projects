package com.orrin.sca.common.service.uaa.web;

import com.orrin.sca.common.service.uaa.dao.SysResourcesRepository;
import com.orrin.sca.common.service.uaa.domain.SysResources;
import com.orrin.sca.common.service.uaa.domain.SysResourcesEntity;
import com.orrin.sca.common.service.uaa.service.SysResourceServiceApi;
import com.orrin.sca.component.privilege.annotation.ResourcePrivilegeEntity;
import com.orrin.sca.framework.core.model.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resource")
public class ResourceController implements SysResourceServiceApi {

    @Autowired
    private SysResourcesRepository sysResourcesRepository;

    @Override
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseResult<SysResources> insert(@RequestBody ResourcePrivilegeEntity resourcePrivilegeEntity) {
        ResponseResult<SysResources> responseResult = new ResponseResult<>();
        responseResult.setResponseCode("00000");
        responseResult.setResponseMsg("");

        int count = sysResourcesRepository.countByGlobalUniqueId(resourcePrivilegeEntity.getGlobalUniqueId());

        if(count > 0){
            responseResult.setResponseCode("10000");
            responseResult.setResponseMsg("globalUniqueId repeate !");
            return responseResult;
        }

        SysResourcesEntity sysResourcesEntity = new SysResourcesEntity();
        sysResourcesEntity.setGlobalUniqueId(resourcePrivilegeEntity.getGlobalUniqueId());
        sysResourcesEntity.setResourceDesc(resourcePrivilegeEntity.getResourceDesc());
        sysResourcesEntity.setResourcePath(resourcePrivilegeEntity.getResourcePath());
        sysResourcesEntity.setResourceName(resourcePrivilegeEntity.getResourceName());

        sysResourcesEntity = sysResourcesRepository.saveAndFlush(sysResourcesEntity);

        responseResult.setData(sysResourcesEntity);

        return responseResult;
    }
}

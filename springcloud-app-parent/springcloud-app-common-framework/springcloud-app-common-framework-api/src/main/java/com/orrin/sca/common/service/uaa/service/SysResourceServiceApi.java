package com.orrin.sca.common.service.uaa.service;

import com.orrin.sca.common.service.uaa.domain.SysResources;
import com.orrin.sca.component.privilege.annotation.ResourcePrivilegeEntity;
import com.orrin.sca.framework.core.model.ResponseResult;

public interface SysResourceServiceApi {
    ResponseResult<SysResources> insertPrivilege(ResourcePrivilegeEntity resourcePrivilegeEntity);
}

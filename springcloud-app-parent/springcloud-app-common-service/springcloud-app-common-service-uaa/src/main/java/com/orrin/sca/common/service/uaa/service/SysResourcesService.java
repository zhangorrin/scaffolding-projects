package com.orrin.sca.common.service.uaa.service;

import com.orrin.sca.common.service.uaa.domain.SysResourcesEntity;
import org.springframework.data.domain.Page;

/**
 * @author Orrin on 2017/7/8.
 */
public interface SysResourcesService {
	Page<SysResourcesEntity> findNoCriteria(Integer page, Integer size);
	//Page<SysResourcesEntity> findCriteria(Integer page, Integer size, SysResourcesEntity sysResources);

}

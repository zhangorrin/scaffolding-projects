package com.orrin.sca.common.service.uaa.service;

import com.orrin.sca.common.service.uaa.domain.SysAuthoritiesResourcesEntity;
import org.springframework.data.domain.Page;

/**
 * @author Orrin on 2017/7/8.
 */
public interface SysAuthoritiesResourcesService {
	Page<SysAuthoritiesResourcesEntity> findNoCriteria(Integer page, Integer size);
	//Page<SysAuthoritiesResourcesEntity> findCriteria(Integer page, Integer size, SysAuthoritiesResourcesEntity sysAuthoritiesResources);

}

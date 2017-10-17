package com.orrin.sca.common.service.uaa.service;

import com.orrin.sca.common.service.uaa.domain.SysAuthoritiesResourcesEntity;
import com.orrin.sca.common.service.uaa.domain.SysResourcesEntity;
import com.orrin.sca.common.service.uaa.web.vo.AuthoritiesAndResources;
import com.orrin.sca.framework.core.model.ResponseResult;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Orrin on 2017/7/8.
 */
public interface SysAuthoritiesResourcesService {
	Page<SysAuthoritiesResourcesEntity> findNoCriteria(Integer page, Integer size);
	//Page<SysAuthoritiesResourcesEntity> findCriteria(Integer page, Integer size, SysAuthoritiesResourcesEntity sysAuthoritiesResources);

	ResponseResult<AuthoritiesAndResources> findAuthoritiesAndResources(String authorityId, String resourceName, Integer page, Integer size);

	Page<SysResourcesEntity> findResourcesByAuthorities(String authorityId, String resourceName, Integer page, Integer size);

	@Transactional
	void deleteByAuthorityId(String authorityId);

	long countByAuthorityId(String authorityId);

	@Transactional
	void deleteByAuthorityIdAndResourceId(String authorityId, String resourceId);
}

package com.orrin.sca.common.service.uaa.service.impl;

import com.orrin.sca.common.service.uaa.dao.SysAuthoritiesRepository;
import com.orrin.sca.common.service.uaa.dao.SysAuthoritiesResourcesRepository;
import com.orrin.sca.common.service.uaa.domain.SysAuthoritiesEntity;
import com.orrin.sca.common.service.uaa.domain.SysAuthoritiesResourcesEntity;
import com.orrin.sca.common.service.uaa.domain.SysResourcesEntity;
import com.orrin.sca.common.service.uaa.service.SysAuthoritiesResourcesService;
import com.orrin.sca.common.service.uaa.web.vo.AuthoritiesAndResources;
import com.orrin.sca.framework.core.model.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Orrin on 2017/7/8.
 */
@Service("sysAuthoritiesResourcesService")
public class SysAuthoritiesResourcesServiceImpl implements SysAuthoritiesResourcesService {

	@Autowired
	private SysAuthoritiesResourcesRepository sysAuthoritiesResourcesRepository;

	@Autowired
	private SysAuthoritiesRepository sysAuthoritiesRepository;

	@Override
	public Page<SysAuthoritiesResourcesEntity> findNoCriteria(Integer page, Integer size) {
		Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "id");
		return sysAuthoritiesResourcesRepository.findAll(pageable);
	}

	@Override
	public ResponseResult<AuthoritiesAndResources> findAuthoritiesAndResources(String authorityId, String resourceName, Integer page, Integer size) {
		ResponseResult<AuthoritiesAndResources> responseResult = new ResponseResult<>();
		responseResult.setResponseCode("00000");
		responseResult.setResponseMsg("");

		AuthoritiesAndResources authoritiesAndResources = new AuthoritiesAndResources();
		SysAuthoritiesEntity authority = sysAuthoritiesRepository.findOne(authorityId);

		if(authority == null){
			responseResult.setResponseCode("10000");
			responseResult.setResponseMsg("could not find authority entity by authorityId");
			return responseResult;
		}

		authoritiesAndResources.setAuthority(authority);
		Page<SysResourcesEntity> resources = this.findResourcesByAuthorities(authorityId,resourceName, page, size);
		authoritiesAndResources.setResources(resources);

		responseResult.setData(authoritiesAndResources);

		return responseResult;
	}

	@Override
	public Page<SysResourcesEntity> findResourcesByAuthorities(String authorityId, String resourceName, Integer page, Integer size) {
		List<SysResourcesEntity> sreList = sysAuthoritiesResourcesRepository.findResourcesByAuthorityId(authorityId,resourceName);
		Page<SysResourcesEntity> srePage = new PageImpl<SysResourcesEntity>(sreList);
		return srePage;
	}

	@Override
	public void deleteByAuthorityId(String authorityId) {
		sysAuthoritiesResourcesRepository.deleteByAuthorityId(authorityId);
	}


}

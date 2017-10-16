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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
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
		List<Object[]> sreList = sysAuthoritiesResourcesRepository.findResourcesByAuthorityId(authorityId,resourceName, page*size, size);

		Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "resourceId");

		List<Object> countOjb = sysAuthoritiesResourcesRepository.countResourcesByAuthorityId(authorityId,resourceName);
		BigInteger count = (countOjb.get(0) instanceof BigInteger) ? (BigInteger)countOjb.get(0) : BigInteger.valueOf(0);

		List<SysResourcesEntity> allSreList = new ArrayList<>();
		for(Object[] obj : sreList){
			String resourceId = obj[0] == null?null:obj[0].toString();
			String resourceType = obj[1] == null?null:obj[1].toString();
			String resourceNameT = obj[2] == null?null:obj[2].toString();
			String resourceDesc = obj[3] == null?null:obj[3].toString();
			String resourcePath = obj[4] == null?null:obj[4].toString();
			Integer priority = obj[5] == null ? null : (Integer) obj[5];
			Boolean enable = obj[6] == null?null:(Boolean)obj[6];
			Boolean issys = obj[7] == null?null:(Boolean)obj[7];
			String moduleId = obj[8] == null?null:obj[8].toString();
			String globalUniqueId = obj[9] == null?null:obj[9].toString();
			String fatherResourceId = obj[10] == null?null:obj[10].toString();
			String icon = obj[11] == null?null:obj[11].toString();
			String requestMethod = obj[12] == null?null:obj[12].toString();
			String createdBy = obj[13] == null?null:obj[13].toString();
			Date createdDate = obj[14] == null?null:(Date)obj[14];
			String lastModifiedBy = obj[15] == null?null:obj[15].toString();
			Date lastModifiedDate = obj[16] == null?null:(Date)obj[16];
			SysResourcesEntity sre = new SysResourcesEntity(resourceId, resourceType, resourceNameT,
					resourceDesc, resourcePath, priority, enable, issys, moduleId, globalUniqueId,
					fatherResourceId, icon, requestMethod, createdBy, createdDate, lastModifiedBy, lastModifiedDate);

			allSreList.add(sre);
		}

		Page<SysResourcesEntity> srePage = new PageImpl<SysResourcesEntity>(allSreList,pageable,count.longValue());
		return srePage;
	}

	@Override
	public void deleteByAuthorityId(String authorityId) {
		sysAuthoritiesResourcesRepository.deleteByAuthorityId(authorityId);
	}


}

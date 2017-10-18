package com.orrin.sca.common.service.uaa.service.impl;

import com.orrin.sca.common.service.uaa.dao.SysRolesAuthoritiesRepository;
import com.orrin.sca.common.service.uaa.domain.SysAuthoritiesEntity;
import com.orrin.sca.common.service.uaa.domain.SysRolesAuthoritiesEntity;
import com.orrin.sca.common.service.uaa.domain.SysRolesEntity;
import com.orrin.sca.common.service.uaa.service.SysAuthoritiesService;
import com.orrin.sca.common.service.uaa.service.SysRolesAuthoritiesService;
import com.orrin.sca.common.service.uaa.service.SysRolesService;
import com.orrin.sca.common.service.uaa.web.vo.RoleAndAuthorities;
import com.orrin.sca.common.service.uaa.web.vo.RoleAuthorityRequestParams;
import com.orrin.sca.component.utils.string.LocalStringUtils;
import com.orrin.sca.framework.core.exception.BusinessException;
import com.orrin.sca.framework.core.model.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Orrin on 2017/7/8.
 */
@Service("sysRolesAuthoritiesService")
public class SysRolesAuthoritiesServiceImpl implements SysRolesAuthoritiesService {

	@Autowired
	private SysRolesAuthoritiesRepository sysRolesAuthoritiesRepository;

	@Autowired
	private SysRolesService sysRolesService;

	@Autowired
	private SysAuthoritiesService sysAuthoritiesService;

	@Override
	public Page<SysRolesAuthoritiesEntity> findNoCriteria(Integer page, Integer size) {
		Pageable pageable = new PageRequest(page, size);
		return sysRolesAuthoritiesRepository.findAll(pageable);
	}

	@Override
	public long countByRoleId(String roleId) {
		return sysRolesAuthoritiesRepository.countByRoleId(roleId);
	}

	@Override
	public SysRolesAuthoritiesEntity findByAuthorityIdAndRoleId(String authorityId, String roleId) {
		return sysRolesAuthoritiesRepository.findByAuthorityIdAndRoleId(authorityId, roleId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteByAuthorityIdAndRoleId(String authorityId, String roleId) {
		sysRolesAuthoritiesRepository.deleteByAuthorityIdAndRoleId(authorityId, roleId);
	}

	@Override
	public ResponseResult<RoleAndAuthorities> findRoleAndAuthorities(String roleId, RoleAuthorityRequestParams roleAuthorityRequestParams) {
		ResponseResult<RoleAndAuthorities> responseResult = new ResponseResult<>();
		responseResult.setResponseCode("00000");
		responseResult.setResponseMsg("");

		RoleAndAuthorities roleAndAuthorities = new RoleAndAuthorities();

		SysRolesEntity roleEntity = sysRolesService.findOne(roleId);
		if(roleEntity == null){
			throw new BusinessException("10000", " can not find role by roleId = " + roleId + " !");
		}
		roleAndAuthorities.setRole(roleEntity);

		Pageable pageable = new PageRequest(roleAuthorityRequestParams.getQueryPage(),roleAuthorityRequestParams.getSize());
		Page<SysAuthoritiesEntity> authorities = this.findAuthoritiesUnderRole(roleId, roleAuthorityRequestParams.getAuthorityMark(), roleAuthorityRequestParams.getAuthorityName(), pageable);
		roleAndAuthorities.setAuthorities(authorities);

		responseResult.setData(roleAndAuthorities);
		return responseResult;
	}

	@Override
	public Page<SysAuthoritiesEntity> findAuthoritiesUnderRole(String roleId, String authorityMark, String authorityName, Pageable pageable) {
		return sysRolesAuthoritiesRepository.findAuthoritiesUnderRole(roleId, authorityMark, authorityName, pageable);
	}

	@Override
	public Page<SysAuthoritiesEntity> findAuthoritiesNotUnderRole(String roleId, RoleAuthorityRequestParams roleAuthorityRequestParams) {
		Pageable pageable = new PageRequest(roleAuthorityRequestParams.getQueryPage(),roleAuthorityRequestParams.getSize());
		return sysRolesAuthoritiesRepository.findAuthoritiesNotUnderRole(roleId, roleAuthorityRequestParams.getAuthorityMark(), roleAuthorityRequestParams.getAuthorityName(), pageable);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseResult<Void> addAuthoritiesUnderRole(String roleId, List<SysAuthoritiesEntity> authorities) {
		ResponseResult<Void> responseResult = new ResponseResult<>();
		responseResult.setResponseCode("00000");
		responseResult.setResponseMsg("");

		if(authorities != null && authorities.size() > 0){
			SysRolesEntity roleCheck = sysRolesService.findOne(roleId);
			if(roleCheck == null){
				throw new BusinessException("10000", " can not find role by roleId = " + roleId + " !");
			}

			SysRolesAuthoritiesEntity sysRolesAuthoritiesEntity = new SysRolesAuthoritiesEntity();
			sysRolesAuthoritiesEntity.setRoleId(roleCheck.getRoleId());

			for(SysAuthoritiesEntity sae : authorities) {
				SysAuthoritiesEntity saeCheck = sysAuthoritiesService.findOne(sae.getAuthorityId());
				if(saeCheck == null) {
					throw new BusinessException("10000", " can not find Authority by authorityId = " + sae.getAuthorityId() + " !");
				}

				sysRolesAuthoritiesEntity.setId(LocalStringUtils.uuidLowerCase());
				sysRolesAuthoritiesEntity.setAuthorityId(saeCheck.getAuthorityId());
				sysRolesAuthoritiesRepository.save(sysRolesAuthoritiesEntity);
			}
		}

		return responseResult;
	}

	/*@Override
	public Page<SysRolesAuthorities> findCriteria(Integer page, Integer size, SysRolesAuthorities sysRolesAuthorities) {
		return null;
	}*/
}

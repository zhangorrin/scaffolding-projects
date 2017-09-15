package com.orrin.sca.common.service.uaa.service.impl;

import com.orrin.sca.common.service.uaa.dao.SysRolesAuthoritiesRepository;
import com.orrin.sca.common.service.uaa.domain.SysRolesAuthoritiesEntity;
import com.orrin.sca.common.service.uaa.service.SysRolesAuthoritiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author Orrin on 2017/7/8.
 */
@Service("sysRolesAuthoritiesService")
public class SysRolesAuthoritiesServiceImpl implements SysRolesAuthoritiesService {

	@Autowired
	private SysRolesAuthoritiesRepository sysRolesAuthoritiesRepository;

	@Override
	public Page<SysRolesAuthoritiesEntity> findNoCriteria(Integer page, Integer size) {
		Pageable pageable = new PageRequest(page, size);
		return sysRolesAuthoritiesRepository.findAll(pageable);
	}

	/*@Override
	public Page<SysRolesAuthorities> findCriteria(Integer page, Integer size, SysRolesAuthorities sysRolesAuthorities) {
		return null;
	}*/
}

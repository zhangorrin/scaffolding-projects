package com.orrin.sca.common.service.uaa.service.impl;

import com.orrin.sca.common.service.uaa.dao.SysAuthoritiesRepository;
import com.orrin.sca.common.service.uaa.domain.SysAuthoritiesEntity;
import com.orrin.sca.common.service.uaa.service.SysAuthoritiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author Orrin on 2017/7/8.
 */
@Service("sysAuthoritiesService")
public class SysAuthoritiesServiceImpl implements SysAuthoritiesService {

	@Autowired
	private SysAuthoritiesRepository sysAuthoritiesRepository;

	@Override
	public Page<SysAuthoritiesEntity> findNoCriteria(Integer page, Integer size) {
		Pageable pageable = new PageRequest(page, size);
		return sysAuthoritiesRepository.findAll(pageable);
	}

	/*@Override
	public Page<SysAuthorities> findCriteria(Integer page, Integer size, SysAuthorities sysAuthorities) {
		return null;
	}*/
}

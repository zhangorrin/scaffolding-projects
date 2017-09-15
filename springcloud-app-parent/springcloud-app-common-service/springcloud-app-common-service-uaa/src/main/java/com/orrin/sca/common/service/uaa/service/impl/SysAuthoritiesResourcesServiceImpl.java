package com.orrin.sca.common.service.uaa.service.impl;

import com.orrin.sca.common.service.uaa.dao.SysAuthoritiesResourcesRepository;
import com.orrin.sca.common.service.uaa.domain.SysAuthoritiesResourcesEntity;
import com.orrin.sca.common.service.uaa.service.SysAuthoritiesResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @author Orrin on 2017/7/8.
 */
@Service("sysAuthoritiesResourcesService")
public class SysAuthoritiesResourcesServiceImpl implements SysAuthoritiesResourcesService {

	@Autowired
	private SysAuthoritiesResourcesRepository sysAuthoritiesResourcesRepository;

	@Override
	public Page<SysAuthoritiesResourcesEntity> findNoCriteria(Integer page, Integer size) {
		Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "id");
		return sysAuthoritiesResourcesRepository.findAll(pageable);
	}


}

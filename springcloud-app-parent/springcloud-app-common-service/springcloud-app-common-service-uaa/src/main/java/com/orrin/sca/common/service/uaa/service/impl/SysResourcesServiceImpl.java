package com.orrin.sca.common.service.uaa.service.impl;

import com.orrin.sca.common.service.uaa.dao.SysResourcesRepository;
import com.orrin.sca.common.service.uaa.domain.SysResourcesEntity;
import com.orrin.sca.common.service.uaa.service.SysResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author Orrin on 2017/7/8.
 */
@Service("sysResourcesService")
public class SysResourcesServiceImpl implements SysResourcesService {
	@Autowired
	private SysResourcesRepository sysResourcesRepository;

	@Override
	public Page<SysResourcesEntity> findNoCriteria(Integer page, Integer size) {
		Pageable pageable = new PageRequest(page, size);
		return sysResourcesRepository.findAll(pageable);
	}

	/*@Override
	public Page<SysResources> findCriteria(Integer page, Integer size, SysResources sysResources) {
		return null;
	}*/
}

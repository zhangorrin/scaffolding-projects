package com.orrin.sca.common.service.uaa.service.impl;

import com.orrin.sca.common.service.uaa.dao.SysRolesMoudlesRepository;
import com.orrin.sca.common.service.uaa.domain.SysRolesMoudlesEntity;
import com.orrin.sca.common.service.uaa.service.SysRolesMoudlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author Orrin on 2017/7/8.
 */
@Service("sysRolesMoudlesService")
public class SysRolesMoudlesServiceImpl implements SysRolesMoudlesService {

	@Autowired
	private SysRolesMoudlesRepository sysRolesMoudlesRepository;

	@Override
	public Page<SysRolesMoudlesEntity> findNoCriteria(Integer page, Integer size) {
		Pageable pageable = new PageRequest(page, size);
		return sysRolesMoudlesRepository.findAll(pageable);
	}

	/*@Override
	public Page<SysRolesMoudles> findCriteria(Integer page, Integer size, SysRolesMoudles sysRolesMoudles) {
		return null;
	}*/
}

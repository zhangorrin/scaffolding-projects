package com.orrin.sca.common.service.uaa.service;

import com.orrin.sca.common.service.uaa.domain.SysRolesEntity;
import org.springframework.data.domain.Page;

/**
 * @author Orrin on 2017/7/8.
 */
public interface SysRolesService {
	Page<SysRolesEntity> findNoCriteria(Integer page, Integer size);
	//Page<SysRolesEntity> findCriteria(Integer page, Integer size, SysRolesEntity sysRoles);
}

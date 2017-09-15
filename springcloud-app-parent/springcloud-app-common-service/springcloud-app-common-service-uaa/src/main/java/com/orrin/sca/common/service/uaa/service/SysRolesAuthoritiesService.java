package com.orrin.sca.common.service.uaa.service;

import com.orrin.sca.common.service.uaa.domain.SysRolesAuthoritiesEntity;
import org.springframework.data.domain.Page;

/**
 * @author Orrin on 2017/7/8.
 */
public interface SysRolesAuthoritiesService {
	Page<SysRolesAuthoritiesEntity> findNoCriteria(Integer page, Integer size);
	//Page<SysRolesAuthoritiesEntity> findCriteria(Integer page, Integer size, SysRolesAuthoritiesEntity sysRolesAuthorities);

}

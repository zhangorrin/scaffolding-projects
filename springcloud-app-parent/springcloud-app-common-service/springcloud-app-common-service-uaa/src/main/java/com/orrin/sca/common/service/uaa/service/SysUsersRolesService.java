package com.orrin.sca.common.service.uaa.service;

import com.orrin.sca.common.service.uaa.domain.SysUsersRolesEntity;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Orrin on 2017/7/8.
 */
public interface SysUsersRolesService {
	Page<SysUsersRolesEntity> findNoCriteria(Integer page, Integer size);
	//Page<SysUsersRolesEntity> findCriteria(Integer page, Integer size, SysUsersRolesEntity sysUsersRoles);

	List<SysUsersRolesEntity> findDistinctByUserId(String userId);
}

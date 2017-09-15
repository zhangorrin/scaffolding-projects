package com.orrin.sca.common.service.uaa.service;

import com.orrin.sca.common.service.uaa.domain.SysUsersEntity;
import org.springframework.data.domain.Page;

/**
 * @author Orrin on 2017/7/8.
 */
public interface SysUsersService {
	Page<SysUsersEntity> findSysUsersNoCriteria(Integer page, Integer size);
	//Page<SysUsersEntity> findSysUsersCriteria(Integer page, Integer size, SysUsersEntity sysUsers);

	SysUsersEntity findByUsername(String username);


}

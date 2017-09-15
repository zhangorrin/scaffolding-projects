package com.orrin.sca.common.service.uaa.service;

import com.orrin.sca.common.service.uaa.domain.SysModulesEntity;
import org.springframework.data.domain.Page;

/**
 * @author Orrin on 2017/7/8.
 */
public interface SysModulesService {

	Page<SysModulesEntity> findNoCriteria(Integer page, Integer size);
	//Page<SysModulesEntity> findCriteria(Integer page, Integer size, SysModulesEntity sysModules);
}

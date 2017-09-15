package com.orrin.sca.common.service.uaa.dao;

import com.orrin.sca.common.service.uaa.domain.SysUsersRolesEntity;
import com.orrin.sca.component.jpa.dao.BaseJPARepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Orrin on 2017/7/8.
 */
@Repository("sysUsersRolesRepository")
public interface SysUsersRolesRepository extends BaseJPARepository<SysUsersRolesEntity, String> {
	List<SysUsersRolesEntity> findDistinctByUserId(String userId);
}

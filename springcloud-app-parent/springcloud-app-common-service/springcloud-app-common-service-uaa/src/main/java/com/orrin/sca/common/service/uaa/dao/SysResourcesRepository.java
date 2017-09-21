package com.orrin.sca.common.service.uaa.dao;

import com.orrin.sca.common.service.uaa.domain.SysResourcesEntity;
import com.orrin.sca.component.jpa.dao.BaseJPARepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Orrin on 2017/7/8.
 */
@Repository("sysResourcesRepository")
public interface SysResourcesRepository extends BaseJPARepository<SysResourcesEntity, String>{

	public int countByGlobalUniqueId(String lobalUniqueId);

	@Query(value = "SELECT DISTINCT r.* FROM sys_authorities_resources ar, sys_roles_authorities ra, sys_users_roles ur, sys_resources r " +
			"WHERE ur.role_id = ra.role_id AND ra.authority_id = ar.authority_id AND ar.resource_id = r.resource_id AND ur.user_id = :userId", nativeQuery = true)
	List<SysResourcesEntity> findAuthResourcesByUserId(@Param("userId") String userId);

	@Query(value = "SELECT DISTINCT s3.resource_path as 'resourcePath' ," +
			" s2.authority_mark as 'authorityMark' ,  " +
			" s3.priority as 'priority' " +
			"FROM sys_authorities_resources s1" +
			" JOIN sys_authorities s2 ON s1.authority_id = s2.authority_id" +
			" JOIN sys_resources s3 ON s1.resource_id = s3.resource_id" +
			" WHERE " +
			" s3.resource_type = 'url'" +
			" ORDER BY" +
			" s3.priority DESC", nativeQuery = true)
	List<Object[]> findAuthResources();
}

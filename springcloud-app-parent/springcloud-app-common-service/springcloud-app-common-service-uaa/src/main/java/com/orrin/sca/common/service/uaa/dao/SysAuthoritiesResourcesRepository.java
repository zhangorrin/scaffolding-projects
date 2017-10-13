package com.orrin.sca.common.service.uaa.dao;

import com.orrin.sca.common.service.uaa.domain.SysAuthoritiesResourcesEntity;
import com.orrin.sca.common.service.uaa.domain.SysResourcesEntity;
import com.orrin.sca.component.jpa.dao.BaseJPARepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Orrin on 2017/7/8.
 */
@Repository("sysAuthoritiesResourcesRepository")
public interface SysAuthoritiesResourcesRepository extends BaseJPARepository<SysAuthoritiesResourcesEntity, String> {
    void deleteByAuthorityId(String authorityId);

    @Query(value = "SELECT DISTINCT sr.* " +
            " FROM sys_resources sr , sys_authorities_resources sar" +
            " WHERE sr.resource_id = sar.resource_id" +
            " AND sr.resource_name = IFNULL(:resourceName, sr.resource_name)" +
            " AND sar.authority_id = :authorityId", nativeQuery = true)
    List<SysResourcesEntity> findResourcesByAuthorityId(@Param("authorityId") String authorityId, @Param("resourceName") String resourceName);

}

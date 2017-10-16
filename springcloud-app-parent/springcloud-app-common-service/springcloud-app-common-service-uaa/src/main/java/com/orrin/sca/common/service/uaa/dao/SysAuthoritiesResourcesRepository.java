package com.orrin.sca.common.service.uaa.dao;

import com.orrin.sca.common.service.uaa.domain.SysAuthoritiesResourcesEntity;
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

    @Query(value = "SELECT DISTINCT" +
            "  sr.resource_id," +
            "  sr.resource_type ," +
            "  sr.resource_name ," +
            "  sr.resource_desc ," +
            "  sr.resource_path ," +
            "  sr.priority ," +
            "  sr.enable ," +
            "  sr.issys ," +
            "  sr.module_id ," +
            "  sr.global_unique_id ," +
            "  sr.father_resource_id ," +
            "  sr.icon ," +
            "  sr.request_method ," +
            "  sr.created_by ," +
            "  sr.created_date ," +
            "  sr.last_modified_by ," +
            "  sr.last_modified_date " +
            " FROM sys_resources sr , sys_authorities_resources sar" +
            " WHERE sr.resource_id = sar.resource_id" +
            " AND sar.authority_id = :authorityId" +
            " AND sr.resource_name LIKE CONCAT('%',IFNULL(:resourceName, sr.resource_name),'%')" +
            " limit  :start, :size",
            countQuery = "SELECT count(1) " +
                    " FROM sys_resources sr , sys_authorities_resources sar" +
                    " WHERE sr.resource_id = sar.resource_id" +
                    " AND sr.resource_name = IFNULL(:resourceName, sr.resource_name)" +
                    " AND sar.authority_id = :authorityId",
            nativeQuery = true)
    List<Object[]> findResourcesByAuthorityId(@Param("authorityId") String authorityId, @Param("resourceName") String resourceName, @Param("start") Integer start, @Param("size") Integer size);

    @Query(value = "SELECT count(1) " +
            " FROM sys_resources sr , sys_authorities_resources sar" +
            " WHERE sr.resource_id = sar.resource_id" +
            " AND sar.authority_id = :authorityId" +
            " AND sr.resource_name LIKE CONCAT('%',IFNULL(:resourceName, sr.resource_name),'%')",
            nativeQuery = true)
    List<Object> countResourcesByAuthorityId(@Param("authorityId") String authorityId, @Param("resourceName") String resourceName);
}

package com.orrin.sca.common.service.uaa.dao;

import com.orrin.sca.common.service.uaa.domain.SysAuthoritiesResourcesEntity;
import com.orrin.sca.component.jpa.dao.BaseJPARepository;
import org.springframework.stereotype.Repository;

/**
 * @author Orrin on 2017/7/8.
 */
@Repository("sysAuthoritiesResourcesRepository")
public interface SysAuthoritiesResourcesRepository extends BaseJPARepository<SysAuthoritiesResourcesEntity, String> {

}

package com.orrin.sca.common.service.uaa.domain;

import com.orrin.sca.component.jpa.model.AbstractAuditingInterface;

public interface SysAuthorities extends AbstractAuditingInterface {

  public String getAuthorityId();

  public String getAuthorityMark();

  public String getAuthority_name();

  public String getAuthorityDesc();

  public String getMessage() ;

  public Long getEnable() ;

  public Long getIssys();

  public String getModuleId();
}

package com.orrin.sca.common.service.uaa.domain;

import com.orrin.sca.component.jpa.model.AbstractAuditingInterface;

public interface SysAuthorities extends AbstractAuditingInterface {

  public String getAuthorityId();

  public String getAuthorityMark();

  public String getAuthorityName();

  public String getAuthorityDesc();

  public String getMessage() ;

  public Boolean getEnable() ;

  public Boolean getIssys();

  public String getModuleId();
}

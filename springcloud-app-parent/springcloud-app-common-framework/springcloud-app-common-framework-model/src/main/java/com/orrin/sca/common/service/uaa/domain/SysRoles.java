package com.orrin.sca.common.service.uaa.domain;

import com.orrin.sca.component.jpa.model.AbstractAuditingInterface;

public interface SysRoles extends AbstractAuditingInterface {

  public String getRoleId();

  public String getRoleName();

  public String getRoleDesc();

  public Boolean getEnable();

  public Boolean getIssys();

  public String getModuleId();

}

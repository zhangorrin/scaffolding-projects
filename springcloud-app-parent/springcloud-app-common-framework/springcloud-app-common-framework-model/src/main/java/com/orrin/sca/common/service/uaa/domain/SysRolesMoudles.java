package com.orrin.sca.common.service.uaa.domain;

import com.orrin.sca.component.jpa.model.AbstractAuditingInterface;

public interface SysRolesMoudles extends AbstractAuditingInterface {

  public String getId();

  public String getModuleId();

  public String getRoleId();

}

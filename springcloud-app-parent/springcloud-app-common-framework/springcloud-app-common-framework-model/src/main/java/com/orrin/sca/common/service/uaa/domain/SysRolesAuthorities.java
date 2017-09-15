package com.orrin.sca.common.service.uaa.domain;

import com.orrin.sca.component.jpa.model.AbstractAuditingInterface;

public interface SysRolesAuthorities extends AbstractAuditingInterface {

  public String getId();

  public String getAuthorityId();

  public String getRoleId();

}

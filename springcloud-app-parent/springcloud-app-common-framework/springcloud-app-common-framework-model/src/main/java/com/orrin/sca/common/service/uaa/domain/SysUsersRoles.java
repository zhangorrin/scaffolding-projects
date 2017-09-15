package com.orrin.sca.common.service.uaa.domain;

import com.orrin.sca.component.jpa.model.AbstractAuditingInterface;

public interface SysUsersRoles extends AbstractAuditingInterface {

  public String getId();

  public String getRoleId();

  public String getUserId();

}

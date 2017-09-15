package com.orrin.sca.common.service.uaa.domain;

import com.orrin.sca.component.jpa.model.AbstractAuditingInterface;

public interface SysAuthoritiesResources extends AbstractAuditingInterface {

  public String getId();

  public String getResourceId();

  public String getAuthorityId();

}

package com.orrin.sca.common.service.uaa.domain;


import com.orrin.sca.component.jpa.model.AbstractAuditingInterface;

public interface SysResources extends AbstractAuditingInterface {

  public String getResourceId();

  public String getResourceType();

  public String getResourceName();

  public String getResourceDesc();

  public String getResourcePath();

  public String getPriority();

  public Long getEnable();

  public Long getIssys();

  public String getModuleId();

}

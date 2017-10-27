package com.orrin.sca.common.service.uaa.domain;


import com.orrin.sca.component.jpa.model.AbstractAuditingInterface;

public interface SysResources extends AbstractAuditingInterface {

  public String getResourceId();

  public String getResourceType();

  public String getResourceName();

  public String getResourceDesc();

  public String getResourcePath();

  public Integer getPriority();

  public Boolean getEnable();

  public Boolean getIssys();

  public String getModuleId();

  public String getGlobalUniqueId();

  public String getFatherResourceId();

  public String getIcon();

  public String getRequestMethod();

  public String getClientId();
}

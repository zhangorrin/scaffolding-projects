package com.orrin.sca.common.service.uaa.domain;

import com.orrin.sca.component.jpa.model.AbstractAuditingInterface;

public interface SysModules extends AbstractAuditingInterface {

  public String getModuleId();

  public String getModuleName();

  public String getModuleDesc();

  public String getModuleType();

  public String getParent();

  public String getModuleUrl();

  public Long getIlevel();

  public Long getLeaf();

  public String getApplication();

  public String getController();

  public Boolean getEnable();

  public Long getPriority() ;
}

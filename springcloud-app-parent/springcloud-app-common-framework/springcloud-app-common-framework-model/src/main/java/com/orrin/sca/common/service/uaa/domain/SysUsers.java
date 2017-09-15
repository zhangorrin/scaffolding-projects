package com.orrin.sca.common.service.uaa.domain;

import com.orrin.sca.component.jpa.model.AbstractAuditingInterface;

import java.sql.Date;

public interface SysUsers extends AbstractAuditingInterface {

  public String getUserId();

  public String getUsername();

  public String getName();

  public String getPassword();

  public Date getLastLogin();

  public Date getDeadline();

  public String getLoginIp();

  public String getVqzjgid();

  public String getVqzjgmc();

  public String getDepId();

  public String getDepName();

  public Long getEnabled();

  public Long getAccountNonExpired();

  public Long getAccountNonLocked();

  public Long getCredentialsNonExpired();

}

package com.orrin.sca.common.service.uaa.domain;

import com.orrin.sca.component.jpa.model.AbstractAuditingInterface;

import java.sql.Timestamp;

public interface PersistentLogins extends AbstractAuditingInterface {

  public String getUsername() ;

  public String getSeries() ;

  public String getToken();

  public Timestamp getLastUsed() ;
}

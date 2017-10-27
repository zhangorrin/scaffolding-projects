package com.orrin.sca.common.service.uaa.domain;

import com.orrin.sca.component.jpa.model.AbstractAuditingInterface;

public interface OauthClientDetails extends AbstractAuditingInterface {

  public String getClientId() ;


  public String getResourceIds() ;


  public String getClientSecret();


  public String getScope();


  public String getAuthorizedGrantTypes();


  public String getWebServerRedirectUri();


  public String getAuthorities();


  public String getAccessTokenValidity();


  public String getRefreshTokenValidity() ;


  public String getAdditionalInformation() ;


  public String getAutoapprove() ;

  public String getClientName() ;

}

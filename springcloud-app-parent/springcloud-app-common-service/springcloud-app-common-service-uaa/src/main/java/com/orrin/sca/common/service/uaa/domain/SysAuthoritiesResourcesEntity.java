package com.orrin.sca.common.service.uaa.domain;

import com.orrin.sca.component.jpa.model.AbstractAuditingEntity;

import javax.persistence.*;

@Entity
@Table(name = "SYS_AUTHORITIES_RESOURCES")
public class SysAuthoritiesResourcesEntity extends AbstractAuditingEntity implements SysAuthoritiesResources {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  @Column(name = "ID")
  private String id;

  @Column(name = "RESOURCE_ID")
  private String resourceId;

  @Column(name = "AUTHORITY_ID")
  private String authorityId;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getResourceId() {
    return resourceId;
  }

  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }

  public String getAuthorityId() {
    return authorityId;
  }

  public void setAuthorityId(String authorityId) {
    this.authorityId = authorityId;
  }
}

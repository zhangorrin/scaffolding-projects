package com.orrin.sca.common.service.uaa.client.domain;

import com.orrin.sca.common.service.uaa.domain.SysRolesAuthorities;
import com.orrin.sca.component.jpa.model.AbstractAuditingEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SYS_ROLES_AUTHORITIES")
public class SysRolesAuthoritiesEntity extends AbstractAuditingEntity implements SysRolesAuthorities {

  private static final long serialVersionUID = 1L;
  @Id
  @Column(name = "ID")
  private String id;

  @Column(name = "AUTHORITY_ID")
  private String authorityId;

  @Column(name = "ROLE_ID")
  private String roleId;

  @Override
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String getAuthorityId() {
    return authorityId;
  }

  public void setAuthorityId(String authorityId) {
    this.authorityId = authorityId;
  }

  @Override
  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }
}

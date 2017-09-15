package com.orrin.sca.common.service.uaa.domain;

import com.orrin.sca.component.jpa.model.AbstractAuditingEntity;

import javax.persistence.*;

@Entity
@Table(name = "SYS_ROLES")
public class SysRoles extends AbstractAuditingEntity {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  @Column(name = "ROLE_ID")
  private String roleId;

  @Column(name = "ROLE_NAME")
  private String roleName;

  @Column(name = "ROLE_DESC")
  private String roleDesc;

  @Column(name = "ENABLE")
  private Long enable;

  @Column(name = "ISSYS")
  private Long issys;

  @Column(name = "MODULE_ID")
  private String moduleId;

  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public String getRoleDesc() {
    return roleDesc;
  }

  public void setRoleDesc(String roleDesc) {
    this.roleDesc = roleDesc;
  }

  public Long getEnable() {
    return enable;
  }

  public void setEnable(Long enable) {
    this.enable = enable;
  }

  public Long getIssys() {
    return issys;
  }

  public void setIssys(Long issys) {
    this.issys = issys;
  }

  public String getModuleId() {
    return moduleId;
  }

  public void setModuleId(String moduleId) {
    this.moduleId = moduleId;
  }
}

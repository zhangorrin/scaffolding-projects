package com.orrin.sca.common.service.uaa.domain;

import com.orrin.sca.component.jpa.model.AbstractAuditingEntity;

import javax.persistence.*;

@Entity
@Table(name = "SYS_AUTHORITIES")
public class SysAuthoritiesEntity extends AbstractAuditingEntity implements SysAuthorities {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue
  @Column(name = "authority_id")
  private String authorityId;

  @Column(name = "authority_mark")
  private String authorityMark;

  @Column(name = "authority_name")
  private String authority_name;

  @Column(name = "authority_desc")
  private String authorityDesc;

  @Column(name = "message")
  private String message;

  @Column(name = "enable")
  private Long enable;

  @Column(name = "issys")
  private Long issys;

  @Column(name = "moduleId")
  private String moduleId;

  public String getAuthorityId() {
    return authorityId;
  }

  public void setAuthorityId(String authorityId) {
    this.authorityId = authorityId;
  }

  public String getAuthorityMark() {
    return authorityMark;
  }

  public void setAuthorityMark(String authorityMark) {
    this.authorityMark = authorityMark;
  }

  public String getAuthority_name() {
    return authority_name;
  }

  public void setAuthority_name(String authority_name) {
    this.authority_name = authority_name;
  }

  public String getAuthorityDesc() {
    return authorityDesc;
  }

  public void setAuthorityDesc(String authorityDesc) {
    this.authorityDesc = authorityDesc;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
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

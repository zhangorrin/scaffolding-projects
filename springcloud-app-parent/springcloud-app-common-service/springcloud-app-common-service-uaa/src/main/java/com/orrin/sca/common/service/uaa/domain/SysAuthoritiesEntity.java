package com.orrin.sca.common.service.uaa.domain;

import com.orrin.sca.component.jpa.model.AbstractAuditingEntity;

public class SysAuthoritiesEntity extends AbstractAuditingEntity implements SysAuthorities {

  private static final long serialVersionUID = 1L;

  private String authorityId;

  private String authorityMark;

  private String authority_name;

  private String authorityDesc;

  private String message;

  private Long enable;

  private Long issys;

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

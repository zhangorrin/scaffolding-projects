package com.orrin.sca.common.service.uaa.domain;

import com.orrin.sca.component.jpa.model.AbstractAuditingEntity;

import javax.persistence.*;

@Entity
@Table(name = "SYS_MODULES")
public class SysModulesEntity extends AbstractAuditingEntity implements SysModules {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  @Column(name = "MODULE_ID")
  private String moduleId;

  @Column(name = "MODULE_NAME")
  private String moduleName;

  @Column(name = "MODULE_DESC")
  private String moduleDesc;

  @Column(name = "MODULE_TYPE")
  private String moduleType;

  @Column(name = "PARENT")
  private String parent;

  @Column(name = "MODULE_URL")
  private String moduleUrl;

  @Column(name = "I_LEVEL")
  private Long ilevel;

  @Column(name = "LEAF")
  private Long leaf;

  @Column(name = "APPLICATION")
  private String application;

  @Column(name = "CONTROLLER")
  private String controller;

  @Column(name = "ENABLE")
  private Long enable;

  @Column(name = "PRIORITY")
  private Long priority;

  public String getModuleId() {
    return moduleId;
  }

  public void setModuleId(String moduleId) {
    this.moduleId = moduleId;
  }

  public String getModuleName() {
    return moduleName;
  }

  public void setModuleName(String moduleName) {
    this.moduleName = moduleName;
  }

  public String getModuleDesc() {
    return moduleDesc;
  }

  public void setModuleDesc(String moduleDesc) {
    this.moduleDesc = moduleDesc;
  }

  public String getModuleType() {
    return moduleType;
  }

  public void setModuleType(String moduleType) {
    this.moduleType = moduleType;
  }

  public String getParent() {
    return parent;
  }

  public void setParent(String parent) {
    this.parent = parent;
  }

  public String getModuleUrl() {
    return moduleUrl;
  }

  public void setModuleUrl(String moduleUrl) {
    this.moduleUrl = moduleUrl;
  }

  public Long getIlevel() {
    return ilevel;
  }

  public void setIlevel(Long ilevel) {
    this.ilevel = ilevel;
  }

  public Long getLeaf() {
    return leaf;
  }

  public void setLeaf(Long leaf) {
    this.leaf = leaf;
  }

  public String getApplication() {
    return application;
  }

  public void setApplication(String application) {
    this.application = application;
  }

  public String getController() {
    return controller;
  }

  public void setController(String controller) {
    this.controller = controller;
  }

  public Long getEnable() {
    return enable;
  }

  public void setEnable(Long enable) {
    this.enable = enable;
  }

  public Long getPriority() {
    return priority;
  }

  public void setPriority(Long priority) {
    this.priority = priority;
  }
}
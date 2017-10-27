package com.orrin.sca.common.service.uaa.domain;


import com.orrin.sca.component.jpa.model.AbstractAuditingEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "SYS_RESOURCES")
public class SysResourcesEntity extends AbstractAuditingEntity implements SysResources {

  private static final long serialVersionUID = 1L;

  public SysResourcesEntity() {
  }

  public SysResourcesEntity(String resourceId, String resourceType, String resourceName,
                            String resourceDesc, String resourcePath, Integer priority,
                            Boolean enable, Boolean issys, String moduleId, String globalUniqueId,
                            String fatherResourceId, String icon, String requestMethod,
                            String createdBy, Date createdDate, String lastModifiedBy, Date lastModifiedDate,
                            String clientId) {
    super(createdBy,createdDate,lastModifiedBy,lastModifiedDate);
    this.resourceId = resourceId;
    this.resourceType = resourceType;
    this.resourceName = resourceName;
    this.resourceDesc = resourceDesc;
    this.resourcePath = resourcePath;
    this.priority = priority;
    this.enable = enable;
    this.issys = issys;
    this.moduleId = moduleId;
    this.globalUniqueId = globalUniqueId;
    this.fatherResourceId = fatherResourceId;
    this.icon = icon;
    this.requestMethod = requestMethod;
    this.clientId = clientId;
  }

  @Id
  @Column(name = "RESOURCE_ID")
  private String resourceId;

  @Column(name = "RESOURCE_TYPE")
  private String resourceType;

  @Column(name = "RESOURCE_NAME")
  private String resourceName;

  @Column(name = "RESOURCE_DESC")
  private String resourceDesc;

  @Column(name = "RESOURCE_PATH")
  private String resourcePath;

  @Column(name = "PRIORITY")
  private Integer priority;

  @Column(name = "ENABLE")
  private Boolean enable;

  @Column(name = "ISSYS")
  private Boolean issys;

  @Column(name = "MODULE_ID")
  private String moduleId;

  @Column(name = "GLOBAL_UNIQUE_ID")
  private String globalUniqueId;

  @Column(name = "FATHER_RESOURCE_ID")
  private String fatherResourceId;

  @Column(name = "ICON")
  private String icon;

  @Column(name = "REQUEST_METHOD")
  private String requestMethod;

  @Column(name = "client_id")
  private String clientId;

  public String getResourceId() {
    return resourceId;
  }

  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }

  public String getResourceType() {
    return resourceType;
  }

  public void setResourceType(String resourceType) {
    this.resourceType = resourceType;
  }

  public String getResourceName() {
    return resourceName;
  }

  public void setResourceName(String resourceName) {
    this.resourceName = resourceName;
  }

  public String getResourceDesc() {
    return resourceDesc;
  }

  public void setResourceDesc(String resourceDesc) {
    this.resourceDesc = resourceDesc;
  }

  public String getResourcePath() {
    return resourcePath;
  }

  public void setResourcePath(String resourcePath) {
    this.resourcePath = resourcePath;
  }

  public Integer getPriority() {
    return priority;
  }

  public void setPriority(Integer priority) {
    this.priority = priority;
  }

  public Boolean getEnable() {
    return enable;
  }

  public void setEnable(Boolean enable) {
    this.enable = enable;
  }

  public Boolean getIssys() {
    return issys;
  }

  public void setIssys(Boolean issys) {
    this.issys = issys;
  }

  public String getModuleId() {
    return moduleId;
  }

  public void setModuleId(String moduleId) {
    this.moduleId = moduleId;
  }

  public String getGlobalUniqueId() {
    return globalUniqueId;
  }

  public void setGlobalUniqueId(String globalUniqueId) {
    this.globalUniqueId = globalUniqueId;
  }

  public String getFatherResourceId() {
    return fatherResourceId;
  }

  public void setFatherResourceId(String fatherResourceId) {
    this.fatherResourceId = fatherResourceId;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getRequestMethod() {
    return requestMethod;
  }

  public void setRequestMethod(String requestMethod) {
    this.requestMethod = requestMethod;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }
}

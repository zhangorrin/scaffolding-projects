package com.orrin.sca.component.menu;

import com.orrin.sca.common.service.uaa.domain.SysResources;

import java.io.Serializable;
import java.util.List;

public class MenuModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;

    private Boolean expand = true;

    private Boolean disabled = false;

    private Boolean disableCheckbox = false;

    private Boolean selected = false;

    private Boolean checked = false;

    private String resourceId;

    private String resourceName;

    private List<MenuModel> children;

    private String icon;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getExpand() {
        return expand;
    }

    public void setExpand(Boolean expand) {
        this.expand = expand;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Boolean getDisableCheckbox() {
        return disableCheckbox;
    }

    public void setDisableCheckbox(Boolean disableCheckbox) {
        this.disableCheckbox = disableCheckbox;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public List<MenuModel> getChildren() {
        return children;
    }

    public void setChildren(List<MenuModel> children) {
        this.children = children;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public static MenuModel fromSysResources(SysResources sysResources) {
        MenuModel menuModel = new MenuModel();
        menuModel.setTitle(sysResources.getResourceName());
        menuModel.setDisabled(!sysResources.getEnable());
        menuModel.setResourceId(sysResources.getResourceId());
        menuModel.setResourceName(sysResources.getResourceName());
        menuModel.setIcon(sysResources.getIcon());
        return menuModel;
    }
}

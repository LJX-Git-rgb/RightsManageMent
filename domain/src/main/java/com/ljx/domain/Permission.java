package com.ljx.domain;

import java.util.List;

public class Permission {
    private String id;
    private String permissionName;
    private String url;
    private List<com.ljx.domain.Role> roles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<com.ljx.domain.Role> getRoles() {
        return roles;
    }

    public void setRoles(List<com.ljx.domain.Role> roles) {
        this.roles = roles;
    }
}

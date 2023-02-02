package com.egortroian.portal.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, ADMIN, AGENCY;

    @Override
    public String getAuthority() {
        return name();
    }
}

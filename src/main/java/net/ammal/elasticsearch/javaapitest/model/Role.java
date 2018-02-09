package net.ammal.elasticsearch.javaapitest.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;
import java.util.UUID;

@JsonDeserialize(builder = Role.Builder.class)
public class Role {

    private final UUID roleId;

    private final String roleName;

    private final List<Permission> permissions;

    private Role(Builder builder) {
        roleId = builder.roleId;
        roleName = builder.roleName;
        permissions = builder.permissions;
    }

    public UUID getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public static final class Builder {
        private UUID roleId;
        private String roleName;
        private List<Permission> permissions;

        public Builder() {
        }

        public Builder withRoleId(UUID val) {
            roleId = val;
            return this;
        }

        public Builder withRoleName(String val) {
            roleName = val;
            return this;
        }

        public Builder withPermissions(List<Permission> val) {
            permissions = val;
            return this;
        }

        public Role build() {
            return new Role(this);
        }
    }
}

package net.ammal.elasticsearch.javaapitest.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = Permission.Builder.class)
public class Permission {

    private final String permissionId;

    private final String permissionName;

    private Permission(Builder builder) {
        permissionId = builder.permissionId;
        permissionName = builder.permissionName;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }


    public static final class Builder {
        private String permissionId;
        private String permissionName;

        public Builder() {
        }

        public Builder withPermissionId(String val) {
            permissionId = val;
            return this;
        }

        public Builder withPermissionName(String val) {
            permissionName = val;
            return this;
        }

        public Permission build() {
            return new Permission(this);
        }
    }
}

package net.ammal.elasticsearch.javaapitest.repository;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import net.ammal.elasticsearch.javaapitest.model.Role;

import java.util.List;
import java.util.UUID;

@JsonDeserialize(builder = UserDocument.Builder.class)
public class UserDocument {

    private final UUID userId;

    private final String firstName;

    private final String lastName;

    private final String emailAddress;

    private final List<Role> roles;

    private UserDocument(Builder builder) {
        userId = builder.userId;
        firstName = builder.firstName;
        lastName = builder.lastName;
        emailAddress = builder.emailAddress;
        roles = builder.roles;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public static final class Builder {
        private UUID userId;
        private String firstName;
        private String lastName;
        private String emailAddress;
        private List<Role> roles;

        public Builder() {
        }

        public Builder withUserId(UUID val) {
            userId = val;
            return this;
        }

        public Builder withFirstName(String val) {
            firstName = val;
            return this;
        }

        public Builder withLastName(String val) {
            lastName = val;
            return this;
        }

        public Builder withEmailAddress(String val) {
            emailAddress = val;
            return this;
        }

        public Builder withRoles(List<Role> val) {
            roles = val;
            return this;
        }

        public UserDocument build() {
            return new UserDocument(this);
        }
    }
}

package net.ammal.elasticsearch.javaapitest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;
import java.util.UUID;

@JsonDeserialize(builder = UserInput.Builder.class)
public class UserInput {

    private final UUID userId;

    private final UserType userType;

    private final String firstName;

    private final String lastName;

    private final String contactNumber;

    private final String emailAddress;

    private final List<Role> roles;

    private UserInput(Builder builder) {
        userId = builder.userId;
        userType = builder.userType;
        firstName = builder.firstName;
        lastName = builder.lastName;
        contactNumber = builder.contactNumber;
        emailAddress = builder.emailAddress;
        roles = builder.roles;
    }

    public UUID getUserId() {
        return userId;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public enum UserType {
        ADMIN,
        APPENDER,
        OBSERVER
    }

    public static final class Builder {
        private UUID userId;
        private UserType userType;
        private String firstName;
        private String lastName;
        private String contactNumber;
        private String emailAddress;
        private List<Role> roles;

        public Builder() {
        }

        public Builder withUserId(UUID val) {
            userId = val;
            return this;
        }

        public Builder withUserType(UserType val) {
            userType = val;
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

        public Builder withContactNumber(String val) {
            contactNumber = val;
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

        public UserInput build() {
            return new UserInput(this);
        }
    }
}


package org.humancellatlas.ingest.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Document
public class Account {

    public static final Account GUEST = new GuestAccount();
    /**
     * A Null Object subclass of Account that represents an unregistered Guest.
     */
    private static class GuestAccount extends Account {

        private static final String EMPTY = "";

        private GuestAccount() {
            super(EMPTY, EMPTY);
            setName(EMPTY);
            addRole(Role.GUEST);
        }

    }

    @Id
    @Getter
    private String id;

    @Indexed(unique=true)
    @Getter
    private String providerReference;

    @Getter
    @Setter
    private String name;

    private Set<Role> roles = new HashSet<>();

    //needed for reflection used by frameworks
    private Account() {}

    public Account(String providerReference) {
        this.providerReference = providerReference;
    }

    public Account(String id, String providerReference) {
        this.id = id;
        this.providerReference = providerReference;
    }

    /**
     * @return an unmodifiable Set of Roles.
     */
    public Set<Role> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    public void addRole(Role role) {
        roles.add(role);
    }

}
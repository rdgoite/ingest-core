package org.humancellatlas.ingest.auth;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class User {

    private String id;
    private String username;
    private String password;

    public User(String username, String password, List<Object> objects) {

    }
}

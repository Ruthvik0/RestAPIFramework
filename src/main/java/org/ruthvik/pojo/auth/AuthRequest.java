package org.ruthvik.pojo.auth;

import lombok.Builder;
import lombok.Data;

@Builder(setterPrefix = "set")
@Data
public class AuthRequest {
    private String email;
    private String password;

}

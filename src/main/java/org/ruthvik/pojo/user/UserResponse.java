package org.ruthvik.pojo.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(setterPrefix = "set")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private long id;
    private String name;
    private String email;
    private String password;
    private String avatar;
    private String role;
    private String creationAt;
    private String updatedAt;
}

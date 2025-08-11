package com.lonovojvladimir.document.user.dto;

import com.lonovojvladimir.document.role.dto.RoleDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private long idUser;
    private String login;
    private String lastName;
    private String firstName;
    private String middleName;
    private boolean deleted;
    private Date created;
    private Set<RoleDto> roles;
    private Boolean keycloak;
}

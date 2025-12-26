package com.fnz.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileResponseDto {

    private String id;
    private String username;
    private String fullName;
    private String email;
}

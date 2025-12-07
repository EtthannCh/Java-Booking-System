package com.example.booking_system.auth.user_account.model;

import java.util.UUID;

import com.example.booking_system.header.HeaderCollections;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAccountCrudDto {
    private UUID userId;
    private String email;
    private String password;
    private String fullName;
    private String phone;
    private Long roleId;

    public UserAccount toRecord(HeaderCollections header) {
        return new UserAccount(null,userId,  email, password, fullName, phone, roleId, null, header.getUserName(),
                header.getUserId(), null, header.getUserName(), header.getUserId());
    }
}

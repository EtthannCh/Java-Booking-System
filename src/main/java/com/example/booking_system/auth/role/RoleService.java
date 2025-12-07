package com.example.booking_system.auth.role;

import java.util.Optional;

import com.example.booking_system.auth.role.model.RoleDto;

public interface RoleService {
    public Optional<RoleDto> findById(Long id);
}

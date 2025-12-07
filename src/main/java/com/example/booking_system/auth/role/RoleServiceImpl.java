package com.example.booking_system.auth.role;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.booking_system.auth.role.model.RoleDto;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<RoleDto> findById(Long id) {
        return roleRepository.findById(id);
    }

}

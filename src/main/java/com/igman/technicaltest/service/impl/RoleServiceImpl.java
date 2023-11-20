package com.igman.technicaltest.service.impl;

import com.igman.technicaltest.constant.ERole;
import com.igman.technicaltest.entity.Role;
import com.igman.technicaltest.repository.RoleRepository;
import com.igman.technicaltest.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role getOrSave(Role role) {
        Optional<Role> optionalRole = roleRepository.findByName(role.getName());
        return optionalRole.orElseGet(() -> roleRepository.save(role));
    }

}

package com.igman.technicaltest.service;

import com.igman.technicaltest.constant.ERole;
import com.igman.technicaltest.entity.Role;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    Role getOrSave(Role role);
}

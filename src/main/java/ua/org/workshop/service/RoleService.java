package ua.org.workshop.service;

import org.springframework.stereotype.Service;
import ua.org.workshop.domain.Role;
import ua.org.workshop.repository.RoleRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RoleService  {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository){
        super();
        this.roleRepository = roleRepository;
    }

    public RoleRepository getRoleRepository() {
        return roleRepository;
    }

    public Role findByCode(String roleStr) {
        return roleRepository.findByCode(roleStr);
    }
}

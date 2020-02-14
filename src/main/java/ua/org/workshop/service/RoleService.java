package ua.org.workshop.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.org.workshop.domain.Role;
import ua.org.workshop.enums.WorkshopError;
import ua.org.workshop.exception.WorkshopException;
import ua.org.workshop.repository.RoleRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoleService {
    private static final Logger logger = LogManager.getLogger(RoleService.class);
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        super();
        this.roleRepository = roleRepository;
    }

    public Role findByCode(String roleStr) throws WorkshopException {
        return roleRepository
                .findByCode(roleStr)
                .orElseThrow(() -> new WorkshopException(WorkshopError.ROLE_NOT_FOUND_ERROR));
    }


    @Transactional(readOnly = false)
    public boolean newRole(Role role) throws WorkshopException {
        try {
            logger.info("Before save");
            roleRepository.save(role);
            logger.info("After save");
        } catch (Exception e) {
            throw new WorkshopException(WorkshopError.DATABASE_CONNECTION_ERROR);
        }
        return true;
    }

    public List<Role> findAll() throws WorkshopException {
        try {
            return roleRepository
                    .findAll();
        } catch (Exception e) {
            throw new WorkshopException(WorkshopError.ROLE_LIST_IS_EMPTY_ERROR);
        }
    }
}

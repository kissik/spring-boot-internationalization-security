package ua.org.workshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.org.workshop.domain.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    Status findByCode(String code);
}

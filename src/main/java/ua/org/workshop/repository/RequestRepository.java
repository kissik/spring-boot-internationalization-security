package ua.org.workshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.org.workshop.domain.Account;
import ua.org.workshop.domain.Request;
import ua.org.workshop.domain.Status;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    Optional<List<Request>> getRequestListByLanguageAndAuthorAndClosed(
            String language,
            Account author,
            boolean closed);

    Optional<List<Request>> getRequestListByLanguageAndClosed(
            String language,
            boolean closed
    );

    Optional<List<Request>> getRequestListByLanguageAndStatusAndClosed(
            String language,
            Status status,
            boolean closed
    );

    Optional<Request> findByIdAndAuthor(
            Long id,
            Account author
    );

    @Override
    Page<Request> findAll(Pageable pageable);
}
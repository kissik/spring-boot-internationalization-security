package ua.org.workshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.org.workshop.domain.Account;
import ua.org.workshop.domain.Request;
import ua.org.workshop.domain.Status;

import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    Page<Request> findAllByLanguageAndAuthor(
            Pageable page,
            String language,
            Account author);

    Page<Request> findAllByLanguage(
            Pageable page,
            String language
    );

    Page<Request> findAllByLanguageAndStatus(
            Pageable page,
            String language,
            Status status
    );

    Optional<Request> findByIdAndAuthor(
            Long id,
            Account author
    );

    @Override
    Page<Request> findAll(Pageable pageable);
}
package ua.org.workshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.org.workshop.domain.Account;
import ua.org.workshop.domain.Request;
import ua.org.workshop.domain.Status;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    public Optional<List<Request>> getRequestListByLanguageAndAuthor(
            String language,
            Account author);

    public Optional<List<Request>> getRequestListByLanguage(
            String language
    );

    public Optional<List<Request>> getRequestListByLanguageAndStatus(
            String language,
            Status status
    );
}
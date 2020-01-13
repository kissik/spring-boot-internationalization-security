package ua.org.workshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.org.workshop.domain.Account;
import ua.org.workshop.domain.RequestHistory;
import ua.org.workshop.domain.Status;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestHistoryRepository extends JpaRepository<RequestHistory, Long> {
    public Optional<List<RequestHistory>> getRequestListHistoryByLanguageAndAuthor(
            String language,
            Account author
    );

    public Optional<List<RequestHistory>> getRequestListHistoryByLanguage(
            String language
    );

    public Optional<List<RequestHistory>> getRequestListHistoryByLanguageAndStatus(
            String language,
            Status status
    );

    public Optional<List<RequestHistory>> getRequestListHistoryByLanguageAndUser(
            String language,
            Account user
    );
}
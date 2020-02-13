package ua.org.workshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.org.workshop.domain.Account;
import ua.org.workshop.domain.HistoryRequest;
import ua.org.workshop.domain.Status;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestHistoryRepository extends JpaRepository<HistoryRequest, Long> {
    public Optional<List<HistoryRequest>> getRequestListHistoryByLanguageAndAuthor(
            String language,
            Account author
    );

    public Optional<List<HistoryRequest>> getRequestListHistoryByLanguage(
            String language
    );

    public Optional<List<HistoryRequest>> getRequestListHistoryByLanguageAndStatus(
            String language,
            Status status
    );

    public Optional<List<HistoryRequest>> getRequestListHistoryByLanguageAndUser(
            String language,
            Account user
    );
}
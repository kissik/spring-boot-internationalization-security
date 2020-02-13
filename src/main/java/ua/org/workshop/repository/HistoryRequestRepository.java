package ua.org.workshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.org.workshop.domain.Account;
import ua.org.workshop.domain.HistoryRequest;
import ua.org.workshop.domain.Status;

@Repository
public interface HistoryRequestRepository extends JpaRepository<HistoryRequest, Long> {
    Page<HistoryRequest> findByLanguageAndAuthor(
            Pageable page,
            String language,
            Account author
    );

    Page<HistoryRequest> findByLanguage(
            Pageable page,
            String language
    );

    Page<HistoryRequest> findByLanguageAndStatus(
            Pageable page,
            String language,
            Status status
    );

    Page<HistoryRequest> findByLanguageAndUser(
            Pageable page,
            String language,
            Account user
    );
}
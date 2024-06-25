package vn.fpt.diamond_shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.fpt.diamond_shop.model.MailHistory;

@Repository
public interface MailHistoryRepository extends JpaRepository<MailHistory, Long> {
    boolean existsByMailAndTypeAndValue(String mail, String type, String value);
}
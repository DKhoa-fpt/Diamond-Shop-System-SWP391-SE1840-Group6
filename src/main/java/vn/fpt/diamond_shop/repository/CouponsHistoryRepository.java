package vn.fpt.diamond_shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.fpt.diamond_shop.model.CouponsHistory;

import java.util.Optional;

@Repository
public interface CouponsHistoryRepository extends JpaRepository<CouponsHistory, Long> {
    Optional<CouponsHistory> findByCouponsCodeAndCustomerId(String couponsCode, Long customerId);
}

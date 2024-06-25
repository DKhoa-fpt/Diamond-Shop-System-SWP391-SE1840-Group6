package vn.fpt.diamond_shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.fpt.diamond_shop.model.EndUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface EndUserRepository extends JpaRepository<EndUser, Long> {
    Optional<EndUser> findEndUserByAccountId(Long accountId);

    EndUser findEndUserByPhoneNumber(String phoneNumber);
}
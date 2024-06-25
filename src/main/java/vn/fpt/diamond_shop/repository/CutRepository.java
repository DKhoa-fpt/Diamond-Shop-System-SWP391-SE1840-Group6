package vn.fpt.diamond_shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.fpt.diamond_shop.model.Cut;

import java.util.UUID;

@Repository
public interface CutRepository extends JpaRepository<Cut, Long> {
}
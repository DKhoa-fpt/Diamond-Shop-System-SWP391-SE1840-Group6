package vn.fpt.diamond_shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.fpt.diamond_shop.model.Polish;

import java.util.UUID;

@Repository
public interface PolishRepository extends JpaRepository<Polish, Long> {
}
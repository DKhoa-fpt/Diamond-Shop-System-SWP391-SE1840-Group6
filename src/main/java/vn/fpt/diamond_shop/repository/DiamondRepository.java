package vn.fpt.diamond_shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.fpt.diamond_shop.model.Diamond;

import java.util.UUID;

public interface DiamondRepository extends JpaRepository<Diamond, Long> {


}
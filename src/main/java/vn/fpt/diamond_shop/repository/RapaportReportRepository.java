package vn.fpt.diamond_shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;
import vn.fpt.diamond_shop.model.RapaportReport;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RapaportReportRepository extends JpaRepository<RapaportReport, Long> {

    @Query(value = "SELECT rr.percent\n" +
            "FROM rapaport_report as rr\n" +
            "         INNER JOIN diamond_shop.clarity c on rr.clarity_id = c.id\n" +
            "         INNER JOIN diamond_shop.color co on rr.color_id = co.id\n" +
            "WHERE 1 = 1\n" +
            "  AND rr.cara_from <= :carra\n" +
            "  AND rr.cara_to >= :carra\n" +
            "  AND c.clarity = :calarity\n" +
            "  AND co.color = :color", nativeQuery = true)
    Optional<Integer> getDiamondPrice(@Param("carra") double carrat,
                                      @Param("calarity") String calarity,
                                      @Param("color") String color);
}
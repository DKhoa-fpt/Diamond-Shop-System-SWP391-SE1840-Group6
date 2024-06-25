package vn.fpt.diamond_shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.fpt.diamond_shop.model.Deliver;
import vn.fpt.diamond_shop.model.Delivery;

import java.util.Date;
import java.util.List;

@Repository
public interface DeliverRepository extends JpaRepository<Deliver, Long> {
    Deliver findByUserId(Long userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Deliver d SET "+
            "d.status = :status,\n" +
            "d.totalOrder = :totalOrder, "+
            "d.totalOrderSuccess = :totalOrderSuccess,"+
            "d.totalOrderFail = :totalOrderFail,"+
            "d.updatedAt = :updatedAt\n" +
            " WHERE " +
            " (:userId is null or d.userId = :userId)"
    )
    void updateDeliverByUserId(@Param("userId")Long userId,
                               @Param("status")String status,
                               @Param("totalOrder")Integer totalOrder,
                               @Param("totalOrderSuccess")Integer totalOrderSuccess,
                               @Param("totalOrderFail")Integer totalOrderFail,
                               @Param("updatedAt") Date updatedAt
                               );

    List<Deliver> findAllByOrderByIdDesc();

    List<Deliver> findAllByStatus(String status);
}
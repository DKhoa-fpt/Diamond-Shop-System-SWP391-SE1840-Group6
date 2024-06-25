package vn.fpt.diamond_shop.repository;

import org.hibernate.criterion.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.fpt.diamond_shop.model.Orders;

import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders, Long> {


    Page<Orders> findAllOrderByOrderByCreatedAtDesc(Pageable pageable);

    Page<Orders> findAllOrderByStatusOrderByCreatedAtDesc(String status, Pageable pageable);

    List<Orders> findAllByOrderByCreatedAtDesc();

    Page<Orders> findAllOrderByCustomerIdOrderByCreatedAtDesc(Long customerId, Pageable pageable);


    @Query("SELECT SUM(o.totalPrice) FROM Orders o WHERE o.customerId = :customerId AND o.status = :status")
    Long getCustomerAmount(Long customerId, String status);
}
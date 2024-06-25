package vn.fpt.diamond_shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.fpt.diamond_shop.model.Cart;
import vn.fpt.diamond_shop.response.ListCartResponse;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query(value = "select new vn.fpt.diamond_shop.response.ListCartResponse(" +
            "c.id as id,\n" +
            "c.userId as customer_id,\n" +
            "j.id as jewelry_id,\n" +
            "c.status as status,\n" +
            "c.quantity as quantity_number,\n" +
            "c.createdAt as created_at,\n" +
            "c.createdBy as created_by,\n" +
            "j.name as jewelry_title,\n" +
            "j.materialPrices as price_items,\n" +
            "i.url as image_url, " +
            "c.size as size"+
            ") FROM \n" +
            "Cart c \n" +
            "left join Jewelry j on\n" +
            "(j.id = c.jewelryId)\n" +
            "left join JewelryType  as jt on\n" +
            "(j.jewelryTypeId = jt.id)\n" +
            "left join Image as i on\n" +
            "(j.imageId = i.id)\n" +
            " WHERE 1 = 1 " +
            "and (:userId is null or c.userId = :userId)"+
            "and c.status = 'active'"
    )
    List<ListCartResponse> getListCartResponse(@Param("userId")Long userId);

    Cart findByUserIdAndAndJewelryId(Long userId, Long JewelryId);
    @Modifying
    @Transactional
    @Query(value = "UPDATE Cart c SET "+
            "c.quantity = :quantity,\n" +
            "c.updatedAt = :updatedAt,\n" +
            "c.status = :status, \n" +
            "c.size = :size \n" +
            " WHERE " +
            " (:userId is null or c.userId = :userId)" +
            "and (:jewelryId is null or c.jewelryId = :jewelryId)"
    )
    void updateByUserIdAndJewelryId(@Param("userId")Long userId,@Param("jewelryId") Long jewelryId,@Param("quantity")  Integer quantity,@Param("updatedAt")  Date updatedAt,@Param("status")  String status,@Param("size")  String size);
    @Query(value = "select new vn.fpt.diamond_shop.response.ListCartResponse("+
            "c.id as id,\n" +
            "c.userId as customer_id,\n" +
            "j.id as jewelry_id,\n" +
            "c.status as status,\n" +
            "c.quantity as quantity_number,\n" +
            "c.createdAt as created_at,\n" +
            "c.createdBy as created_by,\n" +
            "j.name as jewelry_title,\n" +
            "j.materialPrices as price_items,\n" +
            "i.url as image_url, " +
            "c.size as size"+
            ") FROM \n" +
            "Cart c \n" +
            "left join Jewelry j on\n" +
            "(j.id = c.jewelryId)\n" +
            "left join JewelryType  as jt on\n" +
            "(j.jewelryTypeId = jt.id)\n" +
            "left join Image as i on\n" +
            "(j.imageId = i.id)\n"+
            "left join EndUser as eu on\n" +
            "(eu.accountId = c.userId) " +
            " WHERE 1 = 1 " +
            "and (:phoneNumber is null or eu.phoneNumber = :phoneNumber)"+
            "and c.status = 'active'"
    )
    List<ListCartResponse> getListCartByPhoneNumberResponse(@Param("phoneNumber")String phoneNumber);


}
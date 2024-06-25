package vn.fpt.diamond_shop.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListCartResponse {
    private Long id;

    private Long jewelryId;

    private String status;

    private Long customerId;

    private Integer quantityNumber;

    private Date createdAt;

    private String createdBy;

    private Date updatedAt;

    private String updatedBy;

    private String jewelryTitle;

    private Long priceItems;

    private String imageUrl;

    private String size;

    public ListCartResponse(Long id,Long customerId, Long jewelryId, String status, Integer quantityNumber, Date createdAt, String createdBy, String jewelryTitle, Long priceItems, String imageUrl, String size) {
        this.id = id;
        this.customerId = customerId;
        this.jewelryId = jewelryId;
        this.status = status;
        this.quantityNumber = quantityNumber;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.jewelryTitle = jewelryTitle;
        this.priceItems = priceItems;
        this.imageUrl = imageUrl;
        this.size = size;
    }
}

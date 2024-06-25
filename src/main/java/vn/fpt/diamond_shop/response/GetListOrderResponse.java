package vn.fpt.diamond_shop.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AllArgsConstructor
@NoArgsConstructor
public class GetListOrderResponse {
    private Long idJewelry;

    private String jewelryTitle;

    private String jewelryType;

    private Long jewelryTypeId;

    private Integer quantity;

    private Double price;

    private Long imageId;

    private String url;
}

package vn.fpt.diamond_shop.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateDiamondRequest extends BaseRequest {
    private Long id;

    private Long idDiamond;

    private Long jewelryTypeId;

    private Integer quantity;

    private Double materialPrices;

    private Long idGuide;

    private String name;

    private String description;

    @JsonIgnore
    private MultipartFile multipartFile;
}
